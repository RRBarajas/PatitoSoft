package com.patitosoft.service.utils;

import com.patitosoft.projections.SalariesPerPosition;

public class SalariesPerPositionImpl implements SalariesPerPosition {

    private final String position;

    private final Double salary;

    private final String email;

    private final Boolean current;

    public SalariesPerPositionImpl(String position, Double salary, String email, Boolean current) {
        this.position = position;
        this.salary = salary;
        this.email = email;
        this.current = current;
    }

    @Override public String getPosition() {
        return position;
    }

    @Override public Double getSalary() {
        return salary;
    }

    @Override public String getEmail() {
        return email;
    }

    @Override public Boolean getCurrent() {
        return current;
    }
}
