package com.patitosoft.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "employee_position_history")
@Getter
@Setter
public class EmployeePositionHistory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer historyId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "email")
    private Employee employee;

    private Double salary;

    @Column(updatable = false)
    private LocalDateTime createdOn;
}
