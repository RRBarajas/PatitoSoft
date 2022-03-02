package com.patitosoft.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToMany(mappedBy = "employee", fetch = LAZY)
    private List<EmployeePositionHistory> positionHistory;

    private Double salary;

    @OneToOne(fetch = LAZY)
    @PrimaryKeyJoinColumn(name = "email", referencedColumnName = "email")
    private EmployeeContact contact;

    private Boolean deleteFlg;

    @Column(updatable = false)
    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;
}

