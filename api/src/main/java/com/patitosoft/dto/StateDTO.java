package com.patitosoft.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StateDTO {

    @JsonIgnore
    @NotNull
    private Integer stateId;

    @NotNull
    private String stateName;

    @NotNull
    private CountryDTO country;
}
