package com.patitosoft.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patitosoft.api.EmployeeAdminApi;
import com.patitosoft.dto.BirthdaysDTO;
import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeTotalsDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;
import com.patitosoft.dto.EmploymentDTO;
import com.patitosoft.entity.Employee;
import com.patitosoft.entity.EmploymentHistory;
import com.patitosoft.projections.EmployeeForTotals;
import com.patitosoft.projections.EmployeesBirthdays;
import com.patitosoft.repository.EmployeeRepository;
import com.patitosoft.repository.EmploymentHistoryRepository;
import com.patitosoft.service.exception.EmployeeAlreadyExistsException;
import com.patitosoft.service.exception.EmployeeNotFoundException;
import com.patitosoft.service.exception.EmployeeNotInactiveException;
import com.patitosoft.service.exception.InvalidEmailException;
import com.patitosoft.service.exception.InvalidPositionException;
import com.patitosoft.service.exception.MultipleCurrentPositionsException;
import com.patitosoft.service.mapper.EmployeeMapper;

@Service
public class EmployeeService implements EmployeeAdminApi {

    private final EmployeeRepository repository;

    private final EmploymentHistoryRepository historyRepository;

    private final EmployeeMapper mapper;

    public EmployeeService(EmployeeRepository repository,
        EmploymentHistoryRepository historyRepository,
        EmployeeMapper mapper) {
        this.repository = repository;
        this.historyRepository = historyRepository;
        this.mapper = mapper;
    }

    @Override
    public EmployeeDTO getEmployee(String email) {
        Employee employee =
            repository.findByEmailAndDeleteFlgFalse(email.toLowerCase()).orElseThrow(() -> new EmployeeNotFoundException(email));
        return mapper.employeeToEmployeeDTO(employee);
    }

    @Override
    public EmployeeDTO getEmployeeForAdmin(String email) {
        Employee employee = repository.findById(email.toLowerCase()).orElseThrow(() -> new EmployeeNotFoundException(email));
        return mapper.employeeToEmployeeDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByCriteria(String firstName, String lastName, String position) {
        return getEmployeesByCriteriaForAdmin(firstName, lastName, position, false);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByCriteriaForAdmin(String firstName, String lastName, String position, Boolean exEmployees) {
        List<Employee> employees = repository.findByNameAndPosition(Optional.ofNullable(firstName).orElse(""),
            Optional.ofNullable(lastName).orElse(""), position);
        if (exEmployees) {
            return mapper.employeesToEmployeeDTOs(employees);
        }
        return mapper.employeesToEmployeeDTOs(employees.stream().filter(e -> !e.getDeleteFlg()).collect(Collectors.toList()));
    }

    @Override
    public EmployeeTotalsDTO getEmployeeTotals(boolean gender, boolean position, boolean address) {
        List<EmployeeForTotals> totals = repository.findEmployeesForTotals();
        return mapper.employeeTotalsToEmployeeTotalsDTO(totals, gender, position, address);
    }

    @Override
    public BirthdaysDTO getWeeklyBirthdays() {
        LocalDate today = LocalDate.now();
        List<EmployeesBirthdays> birthdays = repository.findByBirthDateBetween(today, today.plusDays(7));
        return mapper.employeesBirthDaysToDTO(birthdays, today);
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        if (repository.existsById(employeeDTO.getEmail().toLowerCase())) {
            throw new EmployeeAlreadyExistsException(employeeDTO.getEmail());
        }
        validateSingleCurrentPosition(employeeDTO);

        Employee newEmployee = mapper.extendedEmployeeDTOToEmployee(employeeDTO);
        newEmployee.setDeleteFlg(false);
        newEmployee.setCreatedOn(LocalDateTime.now());
        newEmployee.setUpdatedOn(null);
        return mapper.employeeToEmployeeDTO(repository.save(newEmployee));
    }

    @Override
    public EmployeeDTO updateEmployee(String email, EmployeeUpdateDTO employeeDTO) {
        // TODO: We must determine what to do with NULLs, should they override the value or ignore them during update?
        Employee oldEmployee = repository.findById(email.toLowerCase()).orElseThrow(() -> new EmployeeNotFoundException(email));
        Employee newEmployee = mapper.employeeUpdateDTOToEmployee(employeeDTO, employeeDTO.getContact(), oldEmployee.getEmail());

        newEmployee.setEmploymentHistory(oldEmployee.getEmploymentHistory());
        newEmployee.setDeleteFlg(oldEmployee.getDeleteFlg());
        newEmployee.setCreatedOn(oldEmployee.getCreatedOn());
        newEmployee.setUpdatedOn(LocalDateTime.now());

        return mapper.employeeToEmployeeDTO(repository.save(newEmployee));
    }

    @Override
    public EmployeeDTO replaceEmployee(String email, EmployeeDTO employeeDTO) {
        validateEmployeeEmail(email, employeeDTO.getEmail());
        validateSingleCurrentPosition(employeeDTO);
        Optional<Employee> currEmployee = repository.findById(email.toLowerCase());

        if (currEmployee.isPresent()) {
            employeeDTO.setCreatedOn(currEmployee.get().getCreatedOn());
            employeeDTO.setUpdatedOn(LocalDateTime.now());
        } else {
            employeeDTO.setCreatedOn(LocalDateTime.now());
            employeeDTO.setUpdatedOn(null);
        }
        Employee savedEmployee = repository.save(mapper.extendedEmployeeDTOToEmployee(employeeDTO));
        return mapper.employeeToEmployeeDTO(savedEmployee);
    }

    @Transactional
    @Override
    public EmployeeDTO assignEmployeePosition(String email, Long position, EmploymentDTO employmentDTO) {
        validateEmployeeIsActive(email);
        validatePositionId(position, employmentDTO);
        Optional<EmploymentHistory> currentPosition = historyRepository.findByEmployeeEmailAndCurrentTrue(email.toLowerCase());

        // TODO: Where is the atomicity? Right now, if the second save fails, the first does not get rolled back
        if (employmentDTO.getCurrentPosition() && currentPosition.isPresent()) {
            EmploymentHistory oldPosition = currentPosition.get();
            oldPosition.setCurrent(Boolean.FALSE);
            oldPosition.setTo(LocalDateTime.now());
            historyRepository.save(oldPosition);
            employmentDTO.setTo(null);
        }
        EmploymentHistory entity = mapper.employmentDTOToEmploymentHistory(employmentDTO);
        entity.setEmployeeEmail(email.toLowerCase());
        historyRepository.saveAndFlush(entity);
        return getEmployeeForAdmin(email);
    }

    @Override
    public void fireEmployee(String email) {
        validateEmployeeIsActive(email);
        repository.fireOrHireEmployee(email.toLowerCase(), true, LocalDateTime.now());
    }

    @Override
    public EmployeeDTO reactivateEmployee(String email) {
        validateEmployeeIsInactive(email);
        repository.fireOrHireEmployee(email.toLowerCase(), false, LocalDateTime.now());
        return getEmployeeForAdmin(email);
    }

    private void validateEmployeeIsActive(String email) {
        if (!repository.existsByEmailAndDeleteFlg(email, Boolean.FALSE)) {
            throw new EmployeeNotFoundException(email);
        }
    }

    private void validateEmployeeIsInactive(String email) {
        if (!repository.existsByEmailAndDeleteFlg(email, Boolean.TRUE)) {
            throw new EmployeeNotInactiveException(email);
        }
    }

    private void validateEmployeeEmail(String oldEmail, String newEmail) {
        if (!oldEmail.equalsIgnoreCase(newEmail)) {
            throw new InvalidEmailException();
        }
    }

    private void validateSingleCurrentPosition(EmployeeDTO employeeDTO) {
        if (employeeDTO.getEmploymentHistory() != null) {
            long currentPositions = employeeDTO.getEmploymentHistory().stream().filter(EmploymentDTO::getCurrentPosition).count();
            if (currentPositions > 1) {
                throw new MultipleCurrentPositionsException(employeeDTO.getEmail());
            }
        }
    }

    private void validatePositionId(Long position, EmploymentDTO employmentDTO) {
        if (!position.equals(employmentDTO.getPositionId())) {
            throw new InvalidPositionException();
        }
    }
}
