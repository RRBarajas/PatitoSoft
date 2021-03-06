package com.patitosoft.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.patitosoft.api.EmployeeAdminApi;
import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeTotalsDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;
import com.patitosoft.dto.EmploymentDTO;

@RestController
@RequestMapping("admin/employees")
public class EmployeeAdminController {

    private final EmployeeAdminApi employeeApi;

    public EmployeeAdminController(EmployeeAdminApi employeeAdminApi) {
        this.employeeApi = employeeAdminApi;
    }

    @GetMapping("/{email}")
    public EmployeeDTO getEmployeeForAdmin(@PathVariable String email) {
        return employeeApi.getEmployeeForAdmin(email);
    }

    @GetMapping
    public List<EmployeeDTO> getEmployeesByCriteriaForAdmin(
        @RequestParam(value = "firstName", required = false) String firstName,
        @RequestParam(value = "lastName", required = false) String lastName,
        @RequestParam(value = "position", required = false) String position,
        @RequestParam(value = "exEmployees", required = false, defaultValue = "false") Boolean exEmployees
    ) {
        return employeeApi.getEmployeesByCriteriaForAdmin(firstName, lastName, position, exEmployees);
    }

    @GetMapping(value = "/totals")
    public EmployeeTotalsDTO getEmployeeTotals(
        @RequestParam(value = "byGender", required = false, defaultValue = "false") boolean gender,
        @RequestParam(value = "byPosition", required = false, defaultValue = "false") boolean position,
        @RequestParam(value = "byAddress", required = false, defaultValue = "false") boolean address) {
        return employeeApi.getEmployeeTotals(gender, position, address);
    }

    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return employeeApi.createEmployee(employeeDTO);
    }

    @PatchMapping("/{email}")
    public EmployeeDTO updateEmployee(@PathVariable String email,
        @RequestBody @Valid EmployeeUpdateDTO employeeDTO) {
        return employeeApi.updateEmployee(email, employeeDTO);
    }

    @PutMapping("/{email}")
    public EmployeeDTO replaceEmployee(@PathVariable String email,
        @RequestBody @Valid EmployeeDTO employeeDTO) {
        return employeeApi.replaceEmployee(email, employeeDTO);
    }

    @PostMapping("/{email}/position")
    public EmployeeDTO assignEmployeePosition(@PathVariable String email,
        @RequestBody @Valid EmploymentDTO employmentDTO) {
        return employeeApi.assignEmployeePosition(email, employmentDTO);
    }

    @DeleteMapping("/{email}")
    public void fireEmployee(@PathVariable String email) {
        employeeApi.fireEmployee(email);
    }

    @PatchMapping("/{email}/reactivate")
    public EmployeeDTO reactivateEmployee(@PathVariable String email) {
        return employeeApi.reactivateEmployee(email);
    }
}
