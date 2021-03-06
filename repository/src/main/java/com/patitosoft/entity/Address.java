package com.patitosoft.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long addressId;

    private String streetAddress;

    private String stateName;

    private String countryName;

}
