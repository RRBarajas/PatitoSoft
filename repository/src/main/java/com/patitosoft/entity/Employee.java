package com.patitosoft.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    private String email;

    private String firstName;

    private String lastName;

    private Character gender;

    @OneToMany(mappedBy = "employee")
    private List<EmploymentHistory> employmentHistory;

    private String personalEmail;

    private String phoneNumber;

    private LocalDate birthDate;

    @ManyToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    private Boolean deleteFlg;

    @Column(updatable = false)
    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;
}
