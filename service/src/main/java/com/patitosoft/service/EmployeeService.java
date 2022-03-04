package com.patitosoft.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patitosoft.api.EmployeeApi;
import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;
import com.patitosoft.entity.Employee;
import com.patitosoft.repository.EmployeeRepository;
import com.patitosoft.service.exception.EmployeeAlreadyExistsException;
import com.patitosoft.service.exception.EmployeeNotFoundException;
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

    @Override
    public EmployeeDTO adminGetEmployee(String email) {
        Employee employee = repository.findById(email).orElseThrow(() -> new EmployeeNotFoundException(email));
        return mapper.employeeToEmployeeDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> allEmployees = repository.findAll();
        return mapper.employeesToEmployeeDTOs(allEmployees);
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        if (repository.findById(employeeDTO.getEmail()).isPresent()) {
            throw new EmployeeAlreadyExistsException(employeeDTO.getEmail());
        }
        Employee newEmployee = mapper.employeeDTOToEmployee(employeeDTO);
        newEmployee.setDeleteFlg(false);
        newEmployee.setCreatedOn(LocalDateTime.now());
        newEmployee.setUpdatedOn(null);
        return mapper.employeeToEmployeeDTO(repository.save(newEmployee));
    }

    @Override
    public EmployeeDTO updateEmployee(String email, EmployeeUpdateDTO employeeDTO) {
        Employee oldEmployee = repository.findById(email).orElseThrow(() -> new EmployeeNotFoundException(email));
        Employee newEmployee = mapper.employeeUpdateDTOToEmployee(employeeDTO);

        // TODO: Implement logic to update positionHistory in case of a change

        newEmployee.setPositionHistory(oldEmployee.getPositionHistory());
        newEmployee.setDeleteFlg(oldEmployee.getDeleteFlg());
        newEmployee.setCreatedOn(oldEmployee.getCreatedOn());
        newEmployee.setUpdatedOn(LocalDateTime.now());
        return mapper.employeeToEmployeeDTO(repository.save(newEmployee));
    }

    @Override
    public EmployeeDTO replaceEmployee(String email, EmployeeDTO employeeDTO) {
        Optional<Employee> currEmployee = repository.findById(email);
        if (currEmployee.isPresent()) {
            employeeDTO.setCreatedOn(currEmployee.get().getCreatedOn());
            employeeDTO.setUpdatedOn(LocalDateTime.now());
        } else {
            employeeDTO.setCreatedOn(LocalDateTime.now());
        }
        employeeDTO.setEmail(email);
        Employee savedEmployee = repository.save(mapper.employeeDTOToEmployee(employeeDTO));
        return mapper.employeeToEmployeeDTO(savedEmployee);
    }

    @Override
    public void fireEmployee(String email) {
        repository.fireOrHireEmployee(email, true, LocalDateTime.now());
    }

    @Override
    public EmployeeDTO reactivateEmployee(String email) {
        repository.fireOrHireEmployee(email, false, LocalDateTime.now());
        return adminGetEmployee(email);
    }
}
