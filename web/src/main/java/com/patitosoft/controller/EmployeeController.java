package com.patitosoft.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patitosoft.api.EmployeeApi;
import com.patitosoft.dto.EmployeeDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeApi employeeService;

    @GetMapping("/{email}")
    public EmployeeDTO getEmployee(@PathVariable String email) {
        return employeeService.getEmployee(email);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }
}
