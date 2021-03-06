package com.patitosoft.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeContactDTO {

    @Email
    private String personalEmail;

    private String phoneNumber;

    private String birthDate;

    @NotNull
    private AddressDTO address;

}
