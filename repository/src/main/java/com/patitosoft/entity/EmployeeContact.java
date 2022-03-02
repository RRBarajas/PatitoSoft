package com.patitosoft.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee_contact")
@Getter
@Setter
public class EmployeeContact {

    @Id
    private String email;

    private String personalEmail;

    private String phoneNumber;

    private Date birthDate;

    private String streetAddress;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
}
