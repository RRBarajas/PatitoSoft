package com.patitosoft.api;

import java.util.List;

import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;

public interface EmployeeApi {

    EmployeeDTO getEmployee(String email);

    EmployeeDTO adminGetEmployee(String email);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(String email, EmployeeUpdateDTO employeeDTO);

    EmployeeDTO replaceEmployee(String email, EmployeeDTO employeeDTO);

    void fireEmployee(String email);

    EmployeeDTO reactivateEmployee(String email);
}
