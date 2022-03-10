package com.patitosoft.service.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.patitosoft.entity.Address;
import com.patitosoft.entity.Employee;
import com.patitosoft.entity.EmploymentHistory;
import com.patitosoft.entity.Position;
import com.patitosoft.projections.EmployeeForTotals;
import com.patitosoft.projections.EmployeesBirthdays;
import com.patitosoft.projections.SalariesPerPosition;

public class EmployeeUtils {

    public static Employee getCompleteEmployee() {
        Employee employee = new Employee();
        employee.setEmail("name@email.com");
        employee.setFirstName("Name");
        employee.setLastName("Last name");
        employee.setGender('M');
        employee.setBirthDate(LocalDate.now());
        employee.setAddress(getAddress());
        employee.setEmploymentHistory(getEmploymentHistoryList());
        employee.setDeleteFlg(Boolean.FALSE);
        employee.setCreatedOn(LocalDateTime.now());
        return employee;
    }

    public static List<Employee> getEmployeesListWithOneNull() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(null);
        return employees;
    }

    public static List<Employee> getEmployeesList() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(getCompleteEmployee());
        return employees;
    }

    public static Address getAddress() {
        Address address = new Address();
        address.setAddressId(1L);
        address.setStreetAddress("Street");
        address.setStateName("State");
        address.setCountryName("Country");
        return address;
    }

    public static Position getPosition() {
        Position position = new Position();
        position.setPositionId(1L);
        position.setPositionName("Position");
        return position;
    }

    public static EmploymentHistory getEmploymentHistory() {
        EmploymentHistory employmentHistory = new EmploymentHistory();
        employmentHistory.setPosition(getPosition());
        employmentHistory.setPositionId(employmentHistory.getPosition().getPositionId());
        employmentHistory.setSalary(100D);
        employmentHistory.setFrom(LocalDateTime.now());
        employmentHistory.setCurrent(Boolean.TRUE);
        return employmentHistory;
    }

    public static List<EmploymentHistory> getEmploymentHistoryList() {
        return List.of(getEmploymentHistory());
    }

    public static List<EmployeeForTotals> getEmployeeForTotals() {
        // To have good coverage, it is important that we have employees meeting the following criteria:
        // Regular employees with ACTIVE positions (employee1)
        // Employees with ONE active position and multiple INACTIVE ones (employee2)
        // A position held by just ONE employee, but marked as INACTIVE (employee4)
        // Employees with NULL position and NULL current flag (employee5)
        // An employee with just ONE "known" but INACTIVE position (employee6)
        EmployeeForTotals employee1 = new EmployeeForTotalsImpl("name@email.com", 'M', "Developer", "Mexico", "Jalisco", Boolean.TRUE);
        EmployeeForTotals employee2 = new EmployeeForTotalsImpl("jane@email.com", 'F', "Developer", "Mexico", "Jalisco", Boolean.FALSE);
        EmployeeForTotals employee3 = new EmployeeForTotalsImpl("jane@email.com", 'F', "Tester", "USA", "California", Boolean.TRUE);
        EmployeeForTotals employee4 = new EmployeeForTotalsImpl("spam@email.com", 'F', "Unknown", "USA", "California", Boolean.FALSE);
        EmployeeForTotals employee5 = new EmployeeForTotalsImpl("unknowm@role.com", 'M', null, "Mexico", "Jalisco", null);
        EmployeeForTotals employee6 = new EmployeeForTotalsImpl("noreply@role.com", 'M', "Tester", "Mexico", "Jalisco", Boolean.FALSE);
        return List.of(employee1, employee2, employee3, employee4, employee5, employee6);
    }

    public static List<SalariesPerPosition> getSalariesPerPosition() {
        SalariesPerPosition employee1 = new SalariesPerPositionImpl("Developer", 10.5, "name@email.com", Boolean.TRUE);
        SalariesPerPosition employee2 = new SalariesPerPositionImpl("Success Coach", 204D, "second@email.com", Boolean.TRUE);
        SalariesPerPosition employee3 = new SalariesPerPositionImpl("Success Coach", 204.0, "third@email.com", Boolean.TRUE);
        SalariesPerPosition employee4 = new SalariesPerPositionImpl("Tester", 1234.56, "fourth@email.com", Boolean.FALSE);
        return List.of(employee1, employee2, employee3, employee4);
    }

    public static List<EmployeesBirthdays> getEmployeesBirthdays() {
        EmployeesBirthdays birthday1 = new EmployeesBirthdaysImpl("name@email.com", "John Doe", LocalDate.now());
        EmployeesBirthdays birthday2 = new EmployeesBirthdaysImpl("second@email.com", "Jane Doe", LocalDate.now().plusDays(4));
        EmployeesBirthdays birthday3 = new EmployeesBirthdaysImpl("third@email.com", "Janine Doe", LocalDate.now().plusDays(6));
        return List.of(birthday1, birthday2, birthday3);
    }
}
