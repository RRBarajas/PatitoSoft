package com.patitosoft.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class EmployeeTotalsDTO {

    private Integer total;

    private Map<Character, Integer> gender;

    private Map<String, Integer> position;

    private Map<String, Map<String, Integer>> address;
}
