package com.patitosoft.api;

import java.util.List;

import com.patitosoft.dto.EmployeeDTO;

public interface EmployeeApi {

    EmployeeDTO getEmployee(String email);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(String email, EmployeeDTO employeeDTO);
}
