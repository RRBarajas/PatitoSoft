package com.patitosoft.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeUpdateDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Character gender;

    @NotNull
    private EmployeeContactDTO contact;
}
