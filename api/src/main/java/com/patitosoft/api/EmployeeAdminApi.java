package com.patitosoft.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.patitosoft.dto.EmployeeDTO;
import com.patitosoft.dto.EmployeeTotalsDTO;
import com.patitosoft.dto.EmployeeUpdateDTO;
import com.patitosoft.dto.PositionDTO;

@FeignClient(path = "employees", name = "${feign.employee-admin-api.name}", url = "${feign.employee-api.url}", primary = false)
public interface EmployeeAdminApi extends EmployeeApi {

    @GetMapping(value = "/admin/{email}")
    EmployeeDTO getEmployeeForAdmin(@PathVariable("email") String email);

    @GetMapping(value = "/admin")
    List<EmployeeDTO> getEmployeesByCriteriaForAdmin(@RequestParam("firstName") String firstName,
        @RequestParam("lastName") String lastName,
        @RequestParam("position") String position,
        @RequestParam("exEmployees") Boolean exEmployees);

    @GetMapping(value = "/totals")
    EmployeeTotalsDTO getEmployeeTotals(@RequestParam("byGender") Boolean gender,
        @RequestParam("byPosition") Boolean position,
        @RequestParam("byAddress") Boolean address);

    @PostMapping
    EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO);

    @PatchMapping("/{email}")
    EmployeeDTO updateEmployee(@PathVariable("email") String email,
        @RequestBody EmployeeUpdateDTO employeeDTO);

    @PutMapping("/{email}")
    EmployeeDTO replaceEmployee(@PathVariable("email") String email,
        @RequestBody EmployeeDTO employeeDTO);

    @PatchMapping("/{email}/position/{position}")
    EmployeeDTO assignEmployeePosition(@PathVariable("email") String email,
        @PathVariable("position") Long position,
        @RequestBody PositionDTO positionDTO);

    @DeleteMapping("/{email}")
    void fireEmployee(@PathVariable("email") String email);

    @PatchMapping("/{email}/reactivate")
    EmployeeDTO reactivateEmployee(@PathVariable("email") String email);
}
