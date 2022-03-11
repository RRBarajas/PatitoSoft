package com.patitosoft.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PositionDTO {

    @NotNull
    private Long positionId;

    @NotNull
    private String positionName;
}
