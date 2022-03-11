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

import org.hibernate.annotations.NotFound;

import lombok.Getter;
import lombok.Setter;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.EAGER;
import static org.hibernate.annotations.NotFoundAction.IGNORE;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    private String email;

    private String firstName;

    private String lastName;

    private Character gender;

    private String personalEmail;

    private String phoneNumber;

    private LocalDate birthDate;

    @ManyToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "employee", cascade = PERSIST)
    @NotFound(action = IGNORE)
    private List<EmploymentHistory> employmentHistory;

    private Boolean deleteFlg;

    @Column(updatable = false)
    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;
}
