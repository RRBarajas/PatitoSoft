package com.patitosoft.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;
import com.patitosoft.entity.Employee;
import com.patitosoft.entity.EmployeePositionHistory;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mappings({
        @Mapping(source = "exEmployee", target = "deleteFlg"),
        @Mapping(source = "contact", target = ".")
    })
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);

    @Mappings({
        @Mapping(source = "deleteFlg", target = "exEmployee"),
        @Mapping(source = ".", target = "contact")
    })
    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    List<EmployeeDTO> employeesToEmployeeDTOs(List<Employee> employee);

    @Mappings({
        @Mapping(source = "contact", target = ".")
    })
    Employee employeeUpdateDTOToEmployee(EmployeeUpdateDTO update);

    @Mappings(
        @Mapping(expression = "java(java.time.LocalDateTime.now())", target = "createdOn")
    )
    EmployeePositionHistory employeeToEmployeePositionHistory(Employee employee);
}
