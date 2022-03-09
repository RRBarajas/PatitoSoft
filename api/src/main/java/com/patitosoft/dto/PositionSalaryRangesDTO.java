package com.patitosoft.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PositionSalaryRangesDTO {

    private String position;

    private Map<Double, Long> salaries;
}
