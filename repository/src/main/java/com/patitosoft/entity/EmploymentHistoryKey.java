package com.patitosoft.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EmploymentHistoryKey implements Serializable {

    private String employeeEmail;

    private Long positionId;

    private LocalDateTime from;
}