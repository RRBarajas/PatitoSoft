package com.patitosoft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patitosoft.api.EmployeeApi;
import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.entity.Employee;
import com.patitosoft.repository.EmployeeRepository;
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
        Employee employee = mapper.employeeDTOToEmployee(employeeDTO);
        Employee savedEmployee = repository.save(employee);
        return mapper.employeeToEmployeeDTO(savedEmployee);
    }

    @Override
    public EmployeeDTO updateEmployee(String email, EmployeeDTO employeeDTO) {
        return null;
    }
}
