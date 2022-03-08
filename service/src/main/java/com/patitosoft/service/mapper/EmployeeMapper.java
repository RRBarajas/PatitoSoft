package com.patitosoft.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeTotalsDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;
import com.patitosoft.dto.PositionDTO;
import com.patitosoft.entity.Employee;
import com.patitosoft.entity.EmployeeForTotals;
import com.patitosoft.entity.EmploymentHistory;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

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

    default EmployeeTotalsDTO employeeTotalsToEmployeeTotalsDTO(List<EmployeeForTotals> employeeForTotals,
        Boolean gender, Boolean position, Boolean address) {
        if (employeeForTotals == null) {
            return null;
        }
        EmployeeTotalsDTO employeeTotalsDTO = new EmployeeTotalsDTO();
        List<EmployeeForTotals> activeEmployees =
            employeeForTotals.stream().filter(e -> isNull(e.getCurrent()) || e.getCurrent()).collect(toList());
        employeeTotalsDTO.setTotal(activeEmployees.size());

        if (gender.equals(Boolean.TRUE)) {
            employeeTotalsDTO.setGender(
                activeEmployees.stream().collect(
                    groupingBy(EmployeeForTotals::getGender, summingInt(e -> 1))));
        }
        if (position.equals(Boolean.TRUE)) {
            employeeTotalsDTO.setPosition(
                activeEmployees.stream().collect(
                    groupingBy(e -> ofNullable(e.getPosition()).orElse("No Job"), summingInt(e -> 1))));
        }
        if (address.equals(Boolean.TRUE)) {
            employeeTotalsDTO.setAddress(activeEmployees.stream().collect(
                groupingBy(EmployeeForTotals::getCountry,
                    groupingBy(EmployeeForTotals::getState, summingInt(e -> 1)))));
        }
        return employeeTotalsDTO;
    }
}
