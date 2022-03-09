package com.patitosoft.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.patitosoft.dto.BirthdaysDTO;
import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeTotalsDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;
import com.patitosoft.dto.PositionDTO;
import com.patitosoft.dto.PositionSalaryRangesDTO;
import com.patitosoft.entity.Employee;
import com.patitosoft.entity.EmployeeForTotals;
import com.patitosoft.entity.EmploymentHistory;
import com.patitosoft.repository.EmployeeRepository;
import com.patitosoft.repository.EmploymentHistoryRepository;
import com.patitosoft.service.exception.EmployeeAlreadyExistsException;
import com.patitosoft.service.exception.EmployeeNotFoundException;
import com.patitosoft.service.exception.InvalidEmailException;
import com.patitosoft.service.exception.InvalidPositionException;
import com.patitosoft.service.exception.MultipleCurrentPositionsException;
import com.patitosoft.service.mapper.EmployeeMapper;
import com.patitosoft.service.utils.EmployeeDTOUtils;
import com.patitosoft.service.utils.EmployeeUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmploymentHistoryRepository historyRepository;

    @Spy
    private EmployeeMapper mapper = EmployeeMapper.INSTANCE;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void getEmployee_ThrowException_IfNoFound() {
        when(employeeRepository.findByEmailAndDeleteFlgFalse(any())).thenReturn(Optional.empty());

        EmployeeNotFoundException exception = assertThrows(
            EmployeeNotFoundException.class,
            () -> employeeService.getEmployee("some@email.com")
        );
        assertEquals("Employee 'some@email.com' does not exist", exception.getMessage());
    }

    @Test
    void getEmployee_ReturnDTO_IfValidEmployee() {
        Optional<Employee> completeEmployee = Optional.of(EmployeeUtils.getCompleteEmployee());
        when(employeeRepository.findByEmailAndDeleteFlgFalse(any())).thenReturn(completeEmployee);

        EmployeeDTO employee = employeeService.getEmployee("some@email.com");

        assertNotNull(employee);
        assertEquals("name@email.com", employee.getEmail());
        assertEquals("Name", employee.getFirstName());
        assertEquals('M', employee.getGender());
    }

    @Test
    void getEmployeeForAdmin_ThrowException_IfNoFound() {
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());

        EmployeeNotFoundException exception = assertThrows(
            EmployeeNotFoundException.class,
            () -> employeeService.getEmployeeForAdmin("some@email.com")
        );
        assertEquals("Employee 'some@email.com' does not exist", exception.getMessage());
    }

    @Test
    void getEmployeeForAdmin_ReturnDTO_IfValidEmployee() {
        Optional<Employee> completeEmployee = Optional.of(EmployeeUtils.getCompleteEmployee());
        when(employeeRepository.findById(any())).thenReturn(completeEmployee);

        EmployeeDTO employee = employeeService.getEmployeeForAdmin("some@email.com");

        assertNotNull(employee);
        assertEquals("name@email.com", employee.getEmail());
        assertEquals("Name", employee.getFirstName());
        assertEquals('M', employee.getGender());
    }

    @Test
    void getEmployeesByCriteria_ReturnEmptyList_IfDeletedEmployee() {
        List<Employee> employeesList = EmployeeUtils.getEmployeesList();
        employeesList.get(0).setDeleteFlg(Boolean.TRUE);
        when(employeeRepository.findByNameAndPosition(any(), any(), any())).thenReturn(employeesList);

        List<EmployeeDTO> employees = employeeService.getEmployeesByCriteria(null, null, null);

        assertNotNull(employees);
        assertEquals(0, employees.size());
    }

    @Test
    void getEmployeesByCriteriaForAdmin_ReturnList_IfIncludeExEmployees() {
        List<Employee> employeesList = EmployeeUtils.getEmployeesList();
        employeesList.get(0).setDeleteFlg(Boolean.TRUE);
        when(employeeRepository.findByNameAndPosition(any(), any(), any())).thenReturn(employeesList);

        List<EmployeeDTO> employees = employeeService.getEmployeesByCriteriaForAdmin(null, null, null, Boolean.TRUE);

        assertNotNull(employees);
        assertEquals(1, employees.size());
    }

    @Test
    void getEmployeeTotals_ReturnOnlyTotal_IfNoGroupings() {
        List<EmployeeForTotals> employeeForTotals = EmployeeUtils.getEmployeeForTotals();
        when(employeeRepository.findEmployeesForTotals()).thenReturn(employeeForTotals);

        EmployeeTotalsDTO employeeTotals = employeeService.getEmployeeTotals(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);

        assertNotNull(employeeTotals);
        assertEquals(5, employeeTotals.getTotal());

        assertNull(employeeTotals.getGender());
        assertNull(employeeTotals.getPosition());
        assertNull(employeeTotals.getAddress());

    }

    @Test
    void getSalaryRangesPerPosition_ReturnEmptyList_IfNoPositions() {
        when(historyRepository.findSalaryRangesByPosition()).thenReturn(Collections.emptyList());

        List<PositionSalaryRangesDTO> positions = employeeService.getSalaryRangesPerPosition();

        assertNotNull(positions);
        assertEquals(0, positions.size());
    }

    @Test
    void getWeeklyBirthdays_ReturnTodayCount_IfTodayBirthday() {
        List<Employee> employeesList = EmployeeUtils.getEmployeesList();
        when(employeeRepository.findByBirthDateBetween(any(), any())).thenReturn(employeesList);

        BirthdaysDTO weeklyBirthdays = employeeService.getWeeklyBirthdays();

        assertNotNull(weeklyBirthdays);
        assertEquals(1, weeklyBirthdays.getToday().size());
        assertEquals(0, weeklyBirthdays.getNextWeek().size());
    }

    @Test
    void getWeeklyBirthdays_ReturnNextWeekCount_IfNextWeekBirthday() {
        List<Employee> employeesList = EmployeeUtils.getEmployeesList();
        employeesList.get(0).setBirthDate(LocalDate.now().plusDays(5));
        when(employeeRepository.findByBirthDateBetween(any(), any())).thenReturn(employeesList);

        BirthdaysDTO weeklyBirthdays = employeeService.getWeeklyBirthdays();

        assertNotNull(weeklyBirthdays);
        assertEquals(0, weeklyBirthdays.getToday().size());
        assertEquals(1, weeklyBirthdays.getNextWeek().size());
    }

    @Test
    void createEmployee_ThrowException_IfEmployeeExists() {
        Optional<Employee> completeEmployee = Optional.of(EmployeeUtils.getCompleteEmployee());
        EmployeeDTO employeeDTO = EmployeeDTOUtils.getEmployeeDTO();
        when(employeeRepository.findById(any())).thenReturn(completeEmployee);

        EmployeeAlreadyExistsException exception = assertThrows(
            EmployeeAlreadyExistsException.class,
            () -> employeeService.createEmployee(employeeDTO)
        );
        assertEquals("Employee 'name@email.com' already exists", exception.getMessage());
    }

    @Test
    void createEmployee_ThrowException_IfMultipleCurrentPositions() {
        EmployeeDTO employeeDTO = EmployeeDTOUtils.getEmployeeDTO();
        employeeDTO.setEmploymentHistory(EmployeeDTOUtils.getDuplicatedEmploymentHistory());
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());

        MultipleCurrentPositionsException exception = assertThrows(
            MultipleCurrentPositionsException.class,
            () -> employeeService.createEmployee(employeeDTO)
        );
        assertEquals("Employee 'name@email.com' cannot have multiple current positions", exception.getMessage());
    }

    @Test
    void createEmployee_ReturnDTO_IfValidEmployee() {
        EmployeeDTO employeeDTO = EmployeeDTOUtils.getEmployeeDTO();
        Employee employee = EmployeeUtils.getCompleteEmployee();
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        when(employeeRepository.save(any())).thenReturn(employee);

        EmployeeDTO newEmployee = employeeService.createEmployee(employeeDTO);

        assertNotNull(newEmployee);
        assertFalse(newEmployee.getExEmployee());
        assertTrue(LocalDateTime.now().isAfter(newEmployee.getCreatedOn()));
        assertNull(newEmployee.getUpdatedOn());
    }

    @Test
    void updateEmployee_ThrowException_IfEmailsDiffer() {
        EmployeeUpdateDTO employeeUpdateDTO = EmployeeDTOUtils.getEmployeeUpdateDTO();

        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class,
            () -> employeeService.updateEmployee("other@email.com", employeeUpdateDTO)
        );
        assertEquals("Email must not differ from the passed parameter and the object", exception.getMessage());
    }

    @Test
    void updateEmployee_ThrowException_IfNoFound() {
        EmployeeUpdateDTO employeeUpdateDTO = EmployeeDTOUtils.getEmployeeUpdateDTO();
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());

        EmployeeNotFoundException exception = assertThrows(
            EmployeeNotFoundException.class,
            () -> employeeService.updateEmployee("name@email.com", employeeUpdateDTO)
        );
        assertEquals("Employee 'name@email.com' does not exist", exception.getMessage());
    }

    @Test
    void updateEmployee_ReturnDTO_IfValidEmployee() {
        Employee oldEmployee = EmployeeUtils.getCompleteEmployee();
        EmployeeUpdateDTO employeeUpdateDTO = EmployeeDTOUtils.getEmployeeUpdateDTO();
        when(employeeRepository.findById(any())).thenReturn(Optional.of(oldEmployee));
        when(employeeRepository.save(any())).thenAnswer(a -> a.getArguments()[0]);

        EmployeeDTO newEmployee = employeeService.updateEmployee("name@email.com", employeeUpdateDTO);

        assertNotNull(newEmployee);
        assertEquals(oldEmployee.getEmploymentHistory().get(0).getPositionId(), newEmployee.getEmploymentHistory().get(0).getPositionId());
        assertEquals(oldEmployee.getDeleteFlg(), newEmployee.getExEmployee());
        assertEquals(oldEmployee.getCreatedOn(), newEmployee.getCreatedOn());
        assertNotNull(newEmployee.getUpdatedOn());
        assertTrue(LocalDateTime.now().isAfter(newEmployee.getUpdatedOn()));
    }

    @Test
    void replaceEmployee_ReturnUpdatedDate_IfEmployeeExists() {
        Employee oldEmployee = EmployeeUtils.getCompleteEmployee();
        EmployeeDTO employeeDTO = EmployeeDTOUtils.getEmployeeDTO();
        when(employeeRepository.findById(any())).thenReturn(Optional.of(oldEmployee));
        when(employeeRepository.save(any())).thenAnswer(a -> a.getArguments()[0]);

        EmployeeDTO newEmployee = employeeService.replaceEmployee("name@email.com", employeeDTO);

        assertNotNull(newEmployee);
        assertEquals(oldEmployee.getCreatedOn(), newEmployee.getCreatedOn());
        assertTrue(LocalDateTime.now().isAfter(newEmployee.getUpdatedOn()));
    }

    @Test
    void replaceEmployee_ReturnNoUpdatedDate_IfNewExists() {
        EmployeeDTO employeeDTO = EmployeeDTOUtils.getEmployeeDTO();
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        when(employeeRepository.save(any())).thenAnswer(a -> a.getArguments()[0]);

        EmployeeDTO newEmployee = employeeService.replaceEmployee("name@email.com", employeeDTO);

        assertNotNull(newEmployee);
        assertTrue(LocalDateTime.now().isAfter(newEmployee.getCreatedOn()));
        assertNull(newEmployee.getUpdatedOn());
        assertEquals(employeeDTO.getEmail(), newEmployee.getEmail());
    }

    @Test
    void assignEmployeePosition_ThrowException_IfPositionsDiffer() {
        PositionDTO positionDTO = EmployeeDTOUtils.getPositionDTO();

        InvalidPositionException exception = assertThrows(
            InvalidPositionException.class,
            () -> employeeService.assignEmployeePosition("name@email.com", 2L, positionDTO)
        );
        assertEquals("Position must be the same in the passed parameter and the object", exception.getMessage());
    }

    @Test
    void assignEmployeePosition_SaveTwice_IfValidPosition() {
        Optional<Employee> oldEmployee = Optional.of(EmployeeUtils.getCompleteEmployee());
        Optional<EmploymentHistory> employmentHistory = Optional.of(EmployeeUtils.getEmploymentHistory());
        PositionDTO positionDTO = EmployeeDTOUtils.getPositionDTO();
        when(employeeRepository.findById(any())).thenReturn(oldEmployee);
        when(historyRepository.findByEmployeeEmailAndCurrentTrue(any())).thenReturn(employmentHistory);

        employeeService.assignEmployeePosition("name@email.com", positionDTO.getPositionId(), positionDTO);

        verify(historyRepository, times(2)).save(any());
        verify(employeeRepository, times(2)).findById(any());
    }

    @Test
    void assignEmployeePosition_SaveOnce_IfNoCurrentPosition() {
        Optional<Employee> oldEmployee = Optional.of(EmployeeUtils.getCompleteEmployee());
        PositionDTO positionDTO = EmployeeDTOUtils.getPositionDTO();
        when(employeeRepository.findById(any())).thenReturn(oldEmployee);
        when(historyRepository.findByEmployeeEmailAndCurrentTrue(any())).thenReturn(Optional.empty());

        employeeService.assignEmployeePosition("name@email.com", positionDTO.getPositionId(), positionDTO);

        verify(historyRepository, times(1)).save(any());
        verify(employeeRepository, times(2)).findById(any());
    }
}