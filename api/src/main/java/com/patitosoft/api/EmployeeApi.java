package com.patitosoft.api;

import java.util.List;

import com.patitosoft.dto.BirthdaysDTO;
import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;
import com.patitosoft.dto.PositionDTO;

public interface EmployeeApi {

    EmployeeDTO getEmployee(String email);

    List<EmployeeDTO> getEmployeesByCriteria(String firstName, String lastName, String position);

    BirthdaysDTO getWeeklyBirthdays();

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(String email, EmployeeUpdateDTO employeeDTO);

    EmployeeDTO replaceEmployee(String email, EmployeeDTO employeeDTO);

    EmployeeDTO assignEmployeePosition(String email, Long position, PositionDTO positionDTO);

    void fireEmployee(String email);

    EmployeeDTO reactivateEmployee(String email);
}
