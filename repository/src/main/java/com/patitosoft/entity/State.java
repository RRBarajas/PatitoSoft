package com.patitosoft.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class State {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer stateId;

    private String stateName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "country_id")
    private Country country;
}
