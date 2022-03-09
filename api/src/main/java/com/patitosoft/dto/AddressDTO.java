package com.patitosoft.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDTO {

    @NotNull
    private Long addressId;

    @NotNull
    private String streetAddress;

    @NotNull
    private String stateName;

    @NotNull
    private String countryName;
}
