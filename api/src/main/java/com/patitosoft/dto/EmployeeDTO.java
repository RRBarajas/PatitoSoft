package com.patitosoft.dto;

import java.time.LocalDateTime;
import java.util.List;

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
public class EmployeeDTO {

    @Email
    @NotNull
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Character gender;

    @NotNull
    private EmployeeContactDTO contact;

    private List<PositionDTO> employmentHistory;

    private Boolean exEmployee;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull
    private LocalDateTime createdOn;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedOn;
}
