package com.patitosoft.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;
import com.patitosoft.dto.PositionDTO;
import com.patitosoft.entity.Employee;
import com.patitosoft.entity.EmploymentHistory;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mappings({
        @Mapping(source = "deleteFlg", target = "exEmployee"),
        @Mapping(source = ".", target = "contact")
    })
    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    @Mappings({
        @Mapping(source = "position", target = "."),
        @Mapping(source = "current", target = "currentPosition")
    })
    PositionDTO employmentHistoryToPositionDTO(EmploymentHistory employmentHistory);

    List<EmployeeDTO> employeesToEmployeeDTOs(List<Employee> employee);

    default Employee extendedEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTOToEmployee(employeeDTO);
        employee.getEmploymentHistory().forEach(e -> {
            e.setEmployeeEmail(employee.getEmail());
            e.setEmployee(employee);
        });
        return employee;
    }

    @Mappings({
        @Mapping(source = "exEmployee", target = "deleteFlg"),
        @Mapping(source = "contact.birthDate", target = "birthDate", dateFormat = "yyyy-MM-dd"),
        @Mapping(source = "contact", target = ".")
    })
    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);

    @Mappings({
        @Mapping(source = ".", target = "position"),
        @Mapping(source = "currentPosition", target = "current")
    })
    EmploymentHistory positionDTOToEmploymentHistory(PositionDTO positionDTO);

    @Mappings({
        @Mapping(source = "contact.birthDate", target = "birthDate", dateFormat = "yyyy-MM-dd"),
        @Mapping(source = "contact", target = ".")
    })
    Employee employeeUpdateDTOToEmployee(EmployeeUpdateDTO update);

}
