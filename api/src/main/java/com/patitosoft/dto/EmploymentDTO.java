package com.patitosoft.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.patitosoft.views.UserType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Getter
@Setter
@NoArgsConstructor
public class EmploymentDTO {

    @NotNull
    private Long positionId;

    @JsonView(value = UserType.Basic.class)
    private String positionName;

    @NotNull
    private Double salary;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull
    private LocalDateTime from;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime to;

    @JsonView(value = UserType.Basic.class)
    private boolean currentPosition;
}
