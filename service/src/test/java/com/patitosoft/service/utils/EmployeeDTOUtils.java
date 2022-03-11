package com.patitosoft.service.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.patitosoft.dto.AddressDTO;
import com.patitosoft.dto.EmployeeContactDTO;
import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;

import static com.patitosoft.service.utils.PositionUtils.getEmploymentListDTO;

public class EmployeeDTOUtils {

    public static EmployeeDTO getEmployeeDTO() {
        EmployeeDTO employee = new EmployeeDTO();
        employee.setEmail("name@email.com");
        employee.setFirstName("Name");
        employee.setLastName("Last name");
        employee.setGender('M');
        employee.setContact(getEmployeeContactDTO());
        employee.setEmploymentHistory(getEmploymentListDTO());
        employee.setExEmployee(Boolean.FALSE);
        employee.setCreatedOn(LocalDateTime.now());
        return employee;
    }

    public static EmployeeUpdateDTO getEmployeeUpdateDTO() {
        EmployeeUpdateDTO employee = new EmployeeUpdateDTO();
        employee.setFirstName("Updated name");
        employee.setLastName("Second last name");
        employee.setGender('M');
        employee.setContact(getEmployeeContactDTO());
        return employee;
    }

    public static EmployeeContactDTO getEmployeeContactDTO() {
        EmployeeContactDTO contact = new EmployeeContactDTO();
        contact.setBirthDate(LocalDate.now().toString());
        contact.setAddress(getAddressDTO());
        return contact;
    }

    public static AddressDTO getAddressDTO() {
        AddressDTO address = new AddressDTO();
        address.setAddressId(1L);
        address.setStreetAddress("Street");
        address.setStateName("State");
        address.setCountryName("Country");
        return address;
    }

}
