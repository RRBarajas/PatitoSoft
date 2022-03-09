package com.patitosoft.service.utils;

import com.patitosoft.entity.SalariesPerPosition;

import lombok.Setter;

@Setter
public class SalariesPerPositionImpl implements SalariesPerPosition {

    private String position;

    private Double salary;

    private String email;

    private Boolean current;

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
