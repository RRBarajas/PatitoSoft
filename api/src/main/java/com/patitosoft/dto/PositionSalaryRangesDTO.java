package com.patitosoft.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PositionSalaryRangesDTO {

    private String position;

    private Map<Double, Long> salaries;
}
