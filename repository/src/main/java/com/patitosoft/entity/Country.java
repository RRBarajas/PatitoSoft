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
public class Country {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer countryId;

    private String countryName;

    private String countryCode;
}
