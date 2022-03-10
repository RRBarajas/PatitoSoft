package com.patitosoft.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BirthdaysDTO {

    private final List<BirthdayPair> today;

    private final Map<LocalDate, List<BirthdayPair>> nextWeek;

    @Getter
    @AllArgsConstructor
    public static class BirthdayPair {

        private final String email;

        private final String name;
    }
}

