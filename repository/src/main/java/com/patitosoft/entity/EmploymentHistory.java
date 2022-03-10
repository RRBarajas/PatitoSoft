package com.patitosoft.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "employment_history")
@Getter
@Setter
@IdClass(EmploymentHistoryKey.class)
public class EmploymentHistory {

    @Id
    private String employeeEmail;

    @Id
    private Long positionId;

    @Id
    @Column(name = "from_dt")
    private LocalDateTime from;

    @ManyToOne
    @MapsId("email")
    @JoinColumn(name = "employeeEmail")
    private Employee employee;

    @ManyToOne(fetch = LAZY)
    @MapsId("positionId")
    @JoinColumn(name = "positionId")
    private Position position;

    private Double salary;

    @Column(name = "to_dt")
    private LocalDateTime to;

    private Boolean current;
}
