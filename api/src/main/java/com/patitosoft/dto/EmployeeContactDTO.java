package com.patitosoft.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeContactDTO {

    @Email
    private String personalEmail;

    private String phoneNumber;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    @NotNull
    private AddressDTO address;

}
