package com.patitosoft.service.mapper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeTotalsDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;
import com.patitosoft.dto.PositionSalaryRangesDTO;
import com.patitosoft.entity.Employee;
import com.patitosoft.projections.EmployeeForTotals;
import com.patitosoft.projections.SalariesPerPosition;
import com.patitosoft.service.utils.EmployeeDTOUtils;
import com.patitosoft.service.utils.EmployeeUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class EmployeeMapperTest {

    EmployeeMapper mapper = EmployeeMapper.INSTANCE;

    @Test
    void employeeToEmployeeDTO_ReturnDTO_IfValidEmployee() {
        Employee employee = EmployeeUtils.getCompleteEmployee();

        EmployeeDTO employeeDTO = mapper.employeeToEmployeeDTO(employee);

        assertNotNull(employeeDTO);
        assertEquals(employee.getEmail(), employeeDTO.getEmail());
        assertEquals(employee.getFirstName(), employeeDTO.getFirstName());
        assertEquals(employee.getGender(), employeeDTO.getGender());

        assertNotNull(employeeDTO.getContact());
        assertEquals(employee.getBirthDate().toString(), employeeDTO.getContact().getBirthDate());

        assertNotNull(employeeDTO.getContact().getAddress());
        assertEquals(employee.getAddress().getAddressId(), employeeDTO.getContact().getAddress().getAddressId());
        assertEquals(employee.getAddress().getCountryName(), employeeDTO.getContact().getAddress().getCountryName());

        assertNotNull(employeeDTO.getEmploymentHistory());
        assertEquals(employee.getEmploymentHistory().size(), employeeDTO.getEmploymentHistory().size());
        assertEquals(employee.getEmploymentHistory().get(0).getPositionId(), employeeDTO.getEmploymentHistory().get(0).getPositionId());
        assertEquals(employee.getEmploymentHistory().get(0).getSalary(), employeeDTO.getEmploymentHistory().get(0).getSalary());
        assertEquals(employee.getEmploymentHistory().get(0).getFrom(), employeeDTO.getEmploymentHistory().get(0).getFrom());
        assertNull(employeeDTO.getEmploymentHistory().get(0).getTo());
        assertEquals(employee.getEmploymentHistory().get(0).getCurrent(), employeeDTO.getEmploymentHistory().get(0).getCurrentPosition());

        assertEquals(employee.getDeleteFlg(), employeeDTO.getExEmployee());
        assertEquals(employee.getCreatedOn(), employeeDTO.getCreatedOn());
        assertNull(employeeDTO.getUpdatedOn());
    }

    @Test
    void employeesToEmployeeDTOs_ReturnListWithNull_IfNullEmployee() {
        List<Employee> employeeList = EmployeeUtils.getEmployeesListWithOneNull();

        List<EmployeeDTO> employeeDTOS = mapper.employeesToEmployeeDTOs(employeeList);

        assertNotNull(employeeDTOS);
        assertEquals(employeeList.size(), employeeDTOS.size());
        assertNull(employeeDTOS.get(0));
    }

    @Test
    void extendedEmployeeDTOToEmployee_ReturnEmployee_IfValidDTO() {
        EmployeeDTO employeeDTO = EmployeeDTOUtils.getEmployeeDTO();

        Employee employee = mapper.extendedEmployeeDTOToEmployee(employeeDTO);

        assertNotNull(employee);
        assertEquals(employeeDTO.getEmail(), employee.getEmail());
        assertEquals(employeeDTO.getFirstName(), employee.getFirstName());
        assertEquals(employeeDTO.getGender(), employee.getGender());
        assertEquals(employeeDTO.getContact().getBirthDate(), employee.getBirthDate().toString());

        assertNotNull(employee.getAddress());
        assertEquals(employeeDTO.getContact().getAddress().getAddressId(), employee.getAddress().getAddressId());
        assertEquals(employeeDTO.getContact().getAddress().getCountryName(), employee.getAddress().getCountryName());

        assertNotNull(employee.getEmploymentHistory());
        assertEquals(employeeDTO.getEmploymentHistory().size(), employee.getEmploymentHistory().size());
        assertEquals(employeeDTO.getEmail(), employee.getEmploymentHistory().get(0).getEmployeeEmail());
        assertNotNull(employee.getEmploymentHistory().get(0).getPosition());
        assertNotNull(employee.getEmploymentHistory().get(0).getEmployee());
        assertEquals(employeeDTO.getEmploymentHistory().get(0).getPositionId(), employee.getEmploymentHistory().get(0).getPositionId());
        assertEquals(employeeDTO.getEmploymentHistory().get(0).getSalary(), employee.getEmploymentHistory().get(0).getSalary());
        assertEquals(employeeDTO.getEmploymentHistory().get(0).getFrom(), employee.getEmploymentHistory().get(0).getFrom());
        assertNull(employeeDTO.getEmploymentHistory().get(0).getTo());
        assertEquals(employeeDTO.getEmploymentHistory().get(0).getCurrentPosition(), employee.getEmploymentHistory().get(0).getCurrent());

        assertEquals(employeeDTO.getExEmployee(), employee.getDeleteFlg());
        assertEquals(employeeDTO.getCreatedOn(), employee.getCreatedOn());
        assertNull(employee.getUpdatedOn());
    }

    @Test
    void employeeUpdateDTOToEmployee_ReturnEmployeeWithSomeNulls_IfValidDTO() {
        EmployeeUpdateDTO employeeUpdateDTO = EmployeeDTOUtils.getEmployeeUpdateDTO();

        Employee employee = mapper.employeeUpdateDTOToEmployee(employeeUpdateDTO);

        assertNotNull(employee);
        assertEquals(employeeUpdateDTO.getEmail(), employee.getEmail());
        assertEquals(employeeUpdateDTO.getFirstName(), employee.getFirstName());
        assertEquals(employeeUpdateDTO.getGender(), employee.getGender());
        assertEquals(employeeUpdateDTO.getContact().getBirthDate(), employee.getBirthDate().toString());

        assertNotNull(employee.getAddress());
        assertEquals(employeeUpdateDTO.getContact().getAddress().getAddressId(), employee.getAddress().getAddressId());
        assertEquals(employeeUpdateDTO.getContact().getAddress().getCountryName(), employee.getAddress().getCountryName());

        assertNull(employee.getEmploymentHistory());
        assertNull(employee.getDeleteFlg());
        assertNull(employee.getCreatedOn());
        assertEquals(employeeUpdateDTO.getUpdatedOn(), employee.getUpdatedOn());
    }

    @Test
    void employeeTotalsToEmployeeTotalsDTO_ReturnAll_IfAllFlagsTrue() {
        List<EmployeeForTotals> employeeForTotals = EmployeeUtils.getEmployeeForTotals();

        EmployeeTotalsDTO employeeTotalsDTO = mapper.employeeTotalsToEmployeeTotalsDTO(employeeForTotals, true, true, true);

        assertNotNull(employeeTotalsDTO);
        assertEquals(5, employeeTotalsDTO.getTotal());

        assertNotNull(employeeTotalsDTO.getGender());
        assertEquals(2, employeeTotalsDTO.getGender().size());
        assertEquals(2, employeeTotalsDTO.getGender().get('F'));
        assertEquals(3, employeeTotalsDTO.getGender().get('M'));

        assertNotNull(employeeTotalsDTO.getPosition());
        assertEquals(1, employeeTotalsDTO.getPosition().get("Developer"));
        assertEquals(1, employeeTotalsDTO.getPosition().get("Tester"));
        assertEquals(3, employeeTotalsDTO.getPosition().get("Unassigned"));

        assertNotNull(employeeTotalsDTO.getAddress());
        assertEquals(2, employeeTotalsDTO.getAddress().get("USA").get("California"));
        assertEquals(4, employeeTotalsDTO.getAddress().get("Mexico").get("Jalisco"));
    }

    @Test
    void employeeTotalsToEmployeeTotalsDTO_ReturnTotal_IfNoFlagTrue() {
        List<EmployeeForTotals> employeeForTotals = EmployeeUtils.getEmployeeForTotals();

        EmployeeTotalsDTO employeeTotalsDTO = mapper.employeeTotalsToEmployeeTotalsDTO(employeeForTotals, false, false, false);

        assertNotNull(employeeTotalsDTO);
        assertEquals(5, employeeTotalsDTO.getTotal());

        assertNull(employeeTotalsDTO.getGender());
        assertNull(employeeTotalsDTO.getPosition());
        assertNull(employeeTotalsDTO.getAddress());
    }

    @Test
    void employeeTotalsToEmployeeTotalsDTO_ReturnGender_IfFlagTrue() {
        List<EmployeeForTotals> employeeForTotals = EmployeeUtils.getEmployeeForTotals();

        EmployeeTotalsDTO employeeTotalsDTO = mapper.employeeTotalsToEmployeeTotalsDTO(employeeForTotals, true, false, false);

        assertNotNull(employeeTotalsDTO);
        assertEquals(5, employeeTotalsDTO.getTotal());

        assertNotNull(employeeTotalsDTO.getGender());
        assertEquals(2, employeeTotalsDTO.getGender().size());
        assertEquals(2, employeeTotalsDTO.getGender().get('F'));
        assertEquals(3, employeeTotalsDTO.getGender().get('M'));

        assertNull(employeeTotalsDTO.getPosition());
        assertNull(employeeTotalsDTO.getAddress());
    }

    @Test
    void employeeTotalsToEmployeeTotalsDTO_ReturnPosition_IfFlagTrue() {
        List<EmployeeForTotals> employeeForTotals = EmployeeUtils.getEmployeeForTotals();

        EmployeeTotalsDTO employeeTotalsDTO = mapper.employeeTotalsToEmployeeTotalsDTO(employeeForTotals, false, true, false);

        assertNotNull(employeeTotalsDTO);
        assertEquals(5, employeeTotalsDTO.getTotal());

        assertNull(employeeTotalsDTO.getGender());
        assertNull(employeeTotalsDTO.getAddress());

        assertNotNull(employeeTotalsDTO.getPosition());
        assertEquals(1, employeeTotalsDTO.getPosition().get("Developer"));
        assertEquals(1, employeeTotalsDTO.getPosition().get("Tester"));
        assertEquals(3, employeeTotalsDTO.getPosition().get("Unassigned"));
    }

    @Test
    void employeeTotalsToEmployeeTotalsDTO_ReturnAddress_IfFlagTrue() {
        List<EmployeeForTotals> employeeForTotals = EmployeeUtils.getEmployeeForTotals();

        EmployeeTotalsDTO employeeTotalsDTO = mapper.employeeTotalsToEmployeeTotalsDTO(employeeForTotals, false, false, true);

        assertNotNull(employeeTotalsDTO);
        assertEquals(5, employeeTotalsDTO.getTotal());

        assertNull(employeeTotalsDTO.getGender());
        assertNull(employeeTotalsDTO.getPosition());

        assertNotNull(employeeTotalsDTO.getAddress());
        assertEquals(2, employeeTotalsDTO.getAddress().get("USA").get("California"));
        assertEquals(4, employeeTotalsDTO.getAddress().get("Mexico").get("Jalisco"));
    }

    @Test
    void salariesPerPositionToDTO_ReturnEmptyList_IfNoPositions() {
        List<PositionSalaryRangesDTO> positions = mapper.salariesPerPositionToDTO(Collections.emptyList());

        assertNotNull(positions);
        assertEquals(0, positions.size());
    }

    @Test
    void salariesPerPositionToDTO_ReturnList_IfPositionWithOrWithoutEmployees() {
        List<SalariesPerPosition> salariesPerPosition = EmployeeUtils.getSalariesPerPosition();

        List<PositionSalaryRangesDTO> positions = mapper.salariesPerPositionToDTO(salariesPerPosition);
        // We sort it to make assertions by index, since the current mapper does not have sorting logic
        positions.sort(Comparator.comparing(PositionSalaryRangesDTO::getPosition));

        assertNotNull(positions);
        assertEquals(3, positions.size());
        assertEquals(1, positions.get(0).getSalaries().size());
        assertEquals(2L, positions.get(1).getSalaries().get(204.0));

        assertEquals("Tester", positions.get(2).getPosition());
        assertNull(positions.get(2).getSalaries());
    }

}