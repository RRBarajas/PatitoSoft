package com.patitosoft.service.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.data.util.Pair;

import com.patitosoft.dto.BirthdaysDTO;
import com.patitosoft.dto.BirthdaysDTO.BirthdayPair;
import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeTotalsDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;
import com.patitosoft.dto.PositionDTO;
import com.patitosoft.dto.PositionSalaryRangesDTO;
import com.patitosoft.entity.Employee;
import com.patitosoft.entity.EmploymentHistory;
import com.patitosoft.projections.EmployeeForTotals;
import com.patitosoft.projections.EmployeesBirthdays;
import com.patitosoft.projections.SalariesPerPosition;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    // TODO: This INSTANCE is not needed since we already have the Mapper, but it "helps" for the tests, validate other options
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

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
        employeeTotalsDTO.setTotal(employeeForTotals.stream().map(EmployeeForTotals::getEmail).distinct().count());

        if (gender.equals(Boolean.TRUE)) {
            // Group the distinct employees by gender
            Map<Character, Long> byGender = employeeForTotals.stream()
                .map(e -> Pair.of(e.getGender(), e.getEmail())).distinct()
                .collect(groupingBy(Pair::getFirst, counting()));
            employeeTotalsDTO.setGender(byGender);
        }
        if (address.equals(Boolean.TRUE)) {
            // Group the distinct employees by country and then by state
            Map<String, Map<String, Long>> byAddress = employeeForTotals.stream()
                .map(e -> Pair.of(e.getCountry(), Pair.of(e.getState(), e.getEmail()))).distinct()
                .collect(groupingBy(Pair::getFirst,
                    groupingBy(c -> c.getSecond().getFirst(), counting())));
            employeeTotalsDTO.setAddress(byAddress);
        }
        if (position.equals(Boolean.TRUE)) {
            Map<String, Long> byPosition = new HashMap<>();

            // Get a map of positions that have at least one active employee
            // If an employee has had multiple positions, it should only be counted once in the active position
            Map<String, List<String>> activePositions = employeeForTotals.stream()
                .filter(e -> nonNull(e.getCurrent()) && e.getCurrent())
                .collect(groupingBy(EmployeeForTotals::getPosition,
                    mapping(EmployeeForTotals::getEmail, toList())));
            activePositions.forEach((k, v) -> byPosition.put(k, (long) v.size()));

            // Get a list of all the employees already counted, to avoid double counting old positions
            List<String> placedEmployees = activePositions.values().stream().flatMap(Collection::stream).collect(toList());

            // If an employee was already counted for an active position, it should not be counted here
            // If an employee has no position at all, it counts towards Unassigned
            // If an employee had multiple positions before, but no active one, it counts towards Unassigned only once
            long inactivePositions = employeeForTotals.stream()
                .map(EmployeeForTotals::getEmail)
                .filter(email -> !placedEmployees.contains(email))
                .distinct().count();
            if (inactivePositions > 0) {
                byPosition.put("Unassigned", inactivePositions);
            }

            employeeTotalsDTO.setPosition(byPosition);
        }
        return employeeTotalsDTO;
    }

    default List<PositionSalaryRangesDTO> salariesPerPositionToDTO(List<SalariesPerPosition> salariesPerPosition) {
        if (salariesPerPosition == null) {
            return null;
        }
        List<PositionSalaryRangesDTO> positionSalaryRangesDTO = new ArrayList<>();

        Map<String, Map<Double, Long>> activePositions = salariesPerPosition.stream()
            .filter(p -> nonNull(p.getCurrent()) && p.getCurrent()).collect(
                groupingBy(SalariesPerPosition::getPosition,
                    groupingBy(SalariesPerPosition::getSalary, counting())));
        activePositions.forEach((k, v) -> positionSalaryRangesDTO.add(new PositionSalaryRangesDTO(k, v)));

        Stream<String> inactivePositions = salariesPerPosition.stream()
            .map(SalariesPerPosition::getPosition)
            .filter(position -> !activePositions.containsKey(position)).distinct();
        inactivePositions.forEach(p -> positionSalaryRangesDTO.add(new PositionSalaryRangesDTO(p, null)));

        return positionSalaryRangesDTO;
    }

    default BirthdaysDTO employeesBirthDaysToDTO(List<EmployeesBirthdays> employees, LocalDate filterDate) {
        if (employees == null) {
            return null;
        }

        // Get a list of the filter date birthdays
        List<BirthdayPair> today = employees.stream()
            .filter(b -> b.getBirthDate().equals(filterDate))
            .map(b -> new BirthdayPair(b.getEmail(), b.getName()))
            .collect(toList());

        // The rest get grouped in lists by their respective (sorted) birthdate
        LinkedHashMap<LocalDate, List<BirthdayPair>> nextWeek = employees.stream()
            .filter(b -> !b.getBirthDate().equals(filterDate))
            .sorted(Comparator.comparing(EmployeesBirthdays::getBirthDate))
            .collect(groupingBy(EmployeesBirthdays::getBirthDate, LinkedHashMap::new,
                mapping(b -> new BirthdayPair(b.getEmail(), b.getName()), toList()))
            );
        return new BirthdaysDTO(today, nextWeek);

    }
}
