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

import com.fasterxml.jackson.annotation.JsonView;
import com.patitosoft.api.EmployeeAdminApi;
import com.patitosoft.dto.BirthdaysDTO;
import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeTotalsDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;
import com.patitosoft.dto.PositionDTO;
import com.patitosoft.dto.PositionSalaryRangesDTO;
import com.patitosoft.views.UserType;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private final EmployeeAdminApi employeeApi;

    public EmployeeController(EmployeeAdminApi employeeAdminApi) {
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
    @JsonView(value = UserType.Basic.class)
    public BirthdaysDTO getWeeklyBirthdays() {
        return employeeApi.getWeeklyBirthdays();
    }

    // TODO: All endpoints after this should only be accessible by an Admin user. Implement security to avoid multiple endpoints.
    @GetMapping("/admin/{email}")
    public EmployeeDTO getEmployeeForAdmin(@PathVariable String email) {
        return employeeApi.getEmployeeForAdmin(email);
    }

    @GetMapping("/admin")
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
        @RequestParam(value = "byGender", required = false, defaultValue = "false") Boolean gender,
        @RequestParam(value = "byPosition", required = false, defaultValue = "false") Boolean position,
        @RequestParam(value = "byAddress", required = false, defaultValue = "false") Boolean address) {
        return employeeApi.getEmployeeTotals(gender, position, address);
    }

    @GetMapping(value = "/position/salaries")
    public List<PositionSalaryRangesDTO> getSalaryRangesPerPosition() {
        return employeeApi.getSalaryRangesPerPosition();
    }

    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        return employeeApi.createEmployee(employeeDTO);
    }

    @PatchMapping("/{email}")
    public EmployeeDTO updateEmployee(@PathVariable String email, @RequestBody @Valid EmployeeUpdateDTO employeeDTO) {
        return employeeApi.updateEmployee(email, employeeDTO);
    }

    @PutMapping("/{email}")
    public EmployeeDTO replaceEmployee(@PathVariable String email, @RequestBody @Valid EmployeeDTO employeeDTO) {
        return employeeApi.replaceEmployee(email, employeeDTO);
    }

    @PatchMapping("/{email}/position/{position}")
    public EmployeeDTO assignEmployeePosition(@PathVariable String email, @PathVariable Long position,
        @RequestBody @Valid PositionDTO positionDTO) {
        return employeeApi.assignEmployeePosition(email, position, positionDTO);
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
