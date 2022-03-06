package com.patitosoft.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patitosoft.api.EmployeeApi;
import com.patitosoft.dto.BirthdaysDTO;
import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;
import com.patitosoft.dto.PositionDTO;
import com.patitosoft.entity.Employee;
import com.patitosoft.repository.EmployeeRepository;
import com.patitosoft.service.exception.EmployeeAlreadyExistsException;
import com.patitosoft.service.exception.EmployeeNotFoundException;
import com.patitosoft.service.exception.InvalidEmailException;
import com.patitosoft.service.exception.MultipleCurrentPositionsException;
import com.patitosoft.service.mapper.EmployeeMapper;

@Service
public class EmployeeService implements EmployeeApi {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private EmployeeMapper mapper;

    @Override
    public EmployeeDTO getEmployee(String email) {
        Employee employee = repository.findByEmailAndDeleteFlgFalse(email).orElseThrow(() -> new EmployeeNotFoundException(email));
        return mapper.employeeToEmployeeDTO(employee);
    }

    public EmployeeDTO getEmployeeForAdmin(String email) {
        Employee employee = repository.findById(email).orElseThrow(() -> new EmployeeNotFoundException(email));
        return mapper.employeeToEmployeeDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByCriteria(String firstName, String lastName, String position) {
        return getEmployeesByCriteriaForAdmin(firstName, lastName, position, false);
    }

    public List<EmployeeDTO> getEmployeesByCriteriaForAdmin(String firstName, String lastName, String position, Boolean exEmployees) {
        List<Employee> employees = repository.findByNameAndPosition(Optional.ofNullable(firstName).orElse(""),
            Optional.ofNullable(lastName).orElse(""), position);
        if (exEmployees) {
            return mapper.employeesToEmployeeDTOs(employees);
        }
        return mapper.employeesToEmployeeDTOs(employees.stream().filter(e -> !e.getDeleteFlg()).collect(Collectors.toList()));
    }

    @Override
    public BirthdaysDTO getWeeklyBirthdays() {
        LocalDate today = LocalDate.now();
        List<Employee> employees = repository.findByBirthDateBetween(today, today.plusDays(7));

        BirthdaysDTO birthdaysDTO = new BirthdaysDTO();
        employees.stream().filter(Objects::nonNull).forEach(e -> {
            if (e.getBirthDate().isEqual(today)) {
                birthdaysDTO.getToday().add(mapper.employeeToEmployeeDTO(e));
            } else {
                birthdaysDTO.getNextWeek().add(mapper.employeeToEmployeeDTO(e));
            }
        });
        return birthdaysDTO;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        if (repository.findById(employeeDTO.getEmail()).isPresent()) {
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
        validateEmployeeEmail(email, employeeDTO.getEmail());

        Employee oldEmployee = repository.findById(email).orElseThrow(() -> new EmployeeNotFoundException(email));
        Employee newEmployee = mapper.employeeUpdateDTOToEmployee(employeeDTO);

        // TODO: Implement logic to update positionHistory in case of a change

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
        Optional<Employee> currEmployee = repository.findById(email);
        if (currEmployee.isPresent()) {
            employeeDTO.setCreatedOn(currEmployee.get().getCreatedOn());
            employeeDTO.setUpdatedOn(LocalDateTime.now());
        } else {
            employeeDTO.setCreatedOn(LocalDateTime.now());
        }
        employeeDTO.setEmail(email);
        Employee savedEmployee = repository.save(mapper.extendedEmployeeDTOToEmployee(employeeDTO));
        return mapper.employeeToEmployeeDTO(savedEmployee);
    }

    @Override
    public void fireEmployee(String email) {
        repository.fireOrHireEmployee(email, true, LocalDateTime.now());
    }

    @Override
    public EmployeeDTO reactivateEmployee(String email) {
        repository.fireOrHireEmployee(email, false, LocalDateTime.now());
        return getEmployeeForAdmin(email);
    }

    private void validateEmployeeEmail(String oldEmail, String newEmail) {
        if (!oldEmail.equals(newEmail)) {
            throw new InvalidEmailException();
        }
    }

    private void validateSingleCurrentPosition(EmployeeDTO employeeDTO) {
        long currentPositions = employeeDTO.getEmploymentHistory().stream().filter(PositionDTO::getCurrentPosition).count();
        if (currentPositions > 1) {
            throw new MultipleCurrentPositionsException(employeeDTO.getEmail());
        }
    }
}
