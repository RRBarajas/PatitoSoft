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
import org.springframework.web.bind.annotation.RestController;

import com.patitosoft.api.EmployeeApi;
import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private EmployeeApi employeeService;

    @GetMapping("/{email}")
    public EmployeeDTO getEmployee(@PathVariable String email) {
        return employeeService.getEmployee(email);
    }

    // TODO: All endpoints after this should only be accessible by an Admin user. We should implement security.
    @GetMapping("/admin/{email}")
    public EmployeeDTO adminGetEmployee(@PathVariable String email) {
        return employeeService.adminGetEmployee(email);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
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
