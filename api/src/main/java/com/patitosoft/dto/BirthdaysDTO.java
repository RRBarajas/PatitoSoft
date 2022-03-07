package com.patitosoft.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.patitosoft.views.UserType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonView(value = UserType.Basic.class)
public class BirthdaysDTO {

    private List<EmployeeDTO> today;

    private List<EmployeeDTO> nextWeek;

    public BirthdaysDTO() {
        this.today = new ArrayList<>();
        this.nextWeek = new ArrayList<>();
    }
}
