package com.patitosoft.service.utils;

import java.time.LocalDate;

import com.patitosoft.projections.EmployeesBirthdays;

public class EmployeesBirthdaysImpl implements EmployeesBirthdays {

    private final String email;

    private final String name;

    private final LocalDate birthDate;

    public EmployeesBirthdaysImpl(String email, String name, LocalDate birthDate) {
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
    }

    @Override public String getEmail() {
        return email;
    }

    @Override public String getName() {
        return name;
    }

    @Override public LocalDate getBirthDate() {
        return birthDate;
    }
}
