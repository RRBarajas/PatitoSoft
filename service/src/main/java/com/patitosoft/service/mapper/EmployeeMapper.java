package com.patitosoft.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.patitosoft.dto.EmployeeContactDTO;
import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeePositionHistoryDTO;
import com.patitosoft.dto.PositionDTO;
import com.patitosoft.entity.Employee;
import com.patitosoft.entity.EmployeeContact;
import com.patitosoft.entity.EmployeePositionHistory;
import com.patitosoft.entity.Position;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    List<EmployeeDTO> employeesToEmployeeDTOs(List<Employee> employee);

    EmployeeContact employeeContactDTOToEmployeeContact(EmployeeContactDTO employeeContactDTO);

    EmployeeContactDTO employeeToEmployeeContactDTO(EmployeeContact employeeContact);

    EmployeePositionHistory employeePositionHistoryDTOToEmployeePositionHistory(EmployeePositionHistoryDTO employeePositionHistoryDTO);

    EmployeePositionHistoryDTO employeePositionHistoryToEmployeePositionHistoryDTO(EmployeePositionHistory employeePositionHistory);

    Position positionDTOToPosition(PositionDTO positionDTO);

    PositionDTO positionToPositionDTO(Position position);
}
