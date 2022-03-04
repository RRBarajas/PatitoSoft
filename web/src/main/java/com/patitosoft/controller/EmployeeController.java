package com.patitosoft.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.patitosoft.dto.BirthdaysDTO;
import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;
import com.patitosoft.service.EmployeeService;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{email}")
    public EmployeeDTO getEmployee(@PathVariable String email) {
        return employeeService.getEmployee(email);
    }

    @GetMapping
    public List<EmployeeDTO> getEmployeesByCriteria(
        @RequestParam(value = "firstName", required = false) String firstName,
        @RequestParam(value = "lastName", required = false) String lastName,
        @RequestParam(value = "position", required = false) String position
    ) {
        return employeeService.getEmployeesByCriteria(firstName, lastName, position);
    }

    @GetMapping("/birthdays")
    public BirthdaysDTO getWeeklyBirthdays() {
        return employeeService.getWeeklyBirthdays();
    }

    // TODO: All endpoints after this should only be accessible by an Admin user. We should implement security.
    @GetMapping("/admin/{email}")
    public EmployeeDTO getEmployeeForAdmin(@PathVariable String email) {
        return employeeService.getEmployeeForAdmin(email);
    }

    @GetMapping("/admin")
    public List<EmployeeDTO> etEmployeesByCriteriaForAdmin(
        @RequestParam(value = "firstName", required = false) String firstName,
        @RequestParam(value = "lastName", required = false) String lastName,
        @RequestParam(value = "position", required = false) String position,
        @RequestParam(value = "exEmployees", required = false, defaultValue = "false") Boolean exEmployees
    ) {
        return employeeService.getEmployeesByCriteriaForAdmin(firstName, lastName, position, exEmployees);
    }

    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }

    @PatchMapping("/{email}")
    public EmployeeDTO updateEmployee(@PathVariable String email, @RequestBody @Valid EmployeeUpdateDTO employeeDTO) {
        return employeeService.updateEmployee(email, employeeDTO);
    }

    @PutMapping("/{email}")
    public EmployeeDTO replaceEmployee(@PathVariable String email, @RequestBody @Valid EmployeeDTO employeeDTO) {
        return employeeService.replaceEmployee(email, employeeDTO);
    }

    @DeleteMapping("/{email}")
    public void fireEmployee(@PathVariable String email) {
        employeeService.fireEmployee(email);
    }

    @PatchMapping("/{email}/reactivate")
    public EmployeeDTO reactivateEmployee(@PathVariable String email) {
        return employeeService.reactivateEmployee(email);
    }
}
