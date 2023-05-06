package com.example.mydividend.model.constants;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum Month {

    JAN("Jan", 1),
    FEB("Feb", 2),
    MAR("Mar", 3),
    APR("Apr", 4),
    MAY("May", 5),
    JUN("Jun", 6),
    JUL("Jul", 7),
    AUG("Aug", 8),
    SEP("Sep", 9),
    OCT("Oct", 10),
    NOV("Nov", 11),
    DEC("Dec", 12);

    private final String monthName;
    private final int number;

    public static int strToNumber(String str) {
        Month month = Arrays.stream(Month.values())
                .filter(m -> m.monthName.equals(str))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("입력된 월의 이름이 올바르지 않습니다."));
        return month.number;
    }
}
