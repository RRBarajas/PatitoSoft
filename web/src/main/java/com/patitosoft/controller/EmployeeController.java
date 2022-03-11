package com.patitosoft.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.patitosoft.api.EmployeeApi;
import com.patitosoft.dto.BirthdaysDTO;
import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.views.UserType;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private final EmployeeApi employeeApi;

    public EmployeeController(EmployeeApi employeeAdminApi) {
        this.employeeApi = employeeAdminApi;
    }

    @GetMapping("/{email}")
    @JsonView(value = UserType.Basic.class)
    public EmployeeDTO getEmployee(@PathVariable String email) {
        return employeeApi.getEmployee(email);
    }

    @GetMapping
    @JsonView(value = UserType.Basic.class)
    public List<EmployeeDTO> getEmployeesByCriteria(
        @RequestParam(value = "firstName", required = false) String firstName,
        @RequestParam(value = "lastName", required = false) String lastName,
        @RequestParam(value = "position", required = false) String position
    ) {
        return employeeApi.getEmployeesByCriteria(firstName, lastName, position);
    }

    @GetMapping("/birthdays")
    public BirthdaysDTO getWeeklyBirthdays() {
        return employeeApi.getWeeklyBirthdays();
    }
}
