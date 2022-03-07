package com.patitosoft.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.patitosoft.views.UserType;

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
    @JsonView(value = UserType.Basic.class)
    private String email;

    @NotNull
    @JsonView(value = UserType.Basic.class)
    private String firstName;

    @NotNull
    @JsonView(value = UserType.Basic.class)
    private String lastName;

    @NotNull
    private Character gender;

    @NotNull
    private EmployeeContactDTO contact;

    @JsonView(value = UserType.Basic.class)
    private List<PositionDTO> employmentHistory;

    private Boolean exEmployee;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull
    private LocalDateTime createdOn;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedOn;
}
