package com.patitosoft.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    private String email;

    private String firstName;

    private String lastName;

    private Character gender;

    @ManyToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "position_id")
    private Position position;

    private Double salary;

    @OneToMany(fetch = LAZY, cascade = REFRESH, mappedBy = "employee")
    private List<EmployeePositionHistory> positionHistory;

    private String personalEmail;

    private String phoneNumber;

    private Date birthDate;

    @ManyToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    private Boolean deleteFlg;

    @Column(updatable = false)
    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;
}
