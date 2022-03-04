package com.patitosoft.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BirthdaysDTO {

    private List<EmployeeDTO> today;

    private List<EmployeeDTO> nextWeek;

    public BirthdaysDTO() {
        this.today = new ArrayList<>();
        this.nextWeek = new ArrayList<>();
    }
}
