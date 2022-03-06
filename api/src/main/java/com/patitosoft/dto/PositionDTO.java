package com.patitosoft.dto;

import java.time.LocalDateTime;

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
public class PositionDTO {

    @NotNull
    private Long positionId;

    @NotNull
    private String positionName;

    @NotNull
    private Double salary;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull
    private LocalDateTime from;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime to;

    private Boolean currentPosition;
}
