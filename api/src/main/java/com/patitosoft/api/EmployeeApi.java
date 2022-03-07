package com.patitosoft.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.patitosoft.dto.BirthdaysDTO;
import com.patitosoft.dto.EmployeeDTO;

@FeignClient(path = "employees", name = "${feign.employee-api.name}", url = "${feign.employee-api.url}")
public interface EmployeeApi {

    @GetMapping(value = "/{email}")
    EmployeeDTO getEmployee(@PathVariable("email") String email);

    @GetMapping(value = "/")
    List<EmployeeDTO> getEmployeesByCriteria(@RequestParam("firstName") String firstName,
        @RequestParam("lastName") String lastName,
        @RequestParam("position") String position);

    @GetMapping("/birthdays")
    BirthdaysDTO getWeeklyBirthdays();
}
