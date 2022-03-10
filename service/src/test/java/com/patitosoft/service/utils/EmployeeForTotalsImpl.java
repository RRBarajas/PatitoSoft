package com.patitosoft.service.utils;

import com.patitosoft.projections.EmployeeForTotals;

public class EmployeeForTotalsImpl implements EmployeeForTotals {

    private final String email;

    private final Character gender;

    private final String position;

    private final String country;

    private final String state;

    private final Boolean current;

    public EmployeeForTotalsImpl(String email, Character gender, String position, String country, String state, Boolean current) {
        this.email = email;
        this.gender = gender;
        this.position = position;
        this.country = country;
        this.state = state;
        this.current = current;
    }

    @Override public String getEmail() {
        return email;
    }

    @Override public Character getGender() {
        return gender;
    }

    @Override public String getPosition() {
        return position;
    }

    @Override public String getCountry() {
        return country;
    }

    @Override public String getState() {
        return state;
    }

    @Override public Boolean getCurrent() {
        return current;
    }
}