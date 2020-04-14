package com.notspend.util;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class DateHelper {

    private DateHelper() {
    }

    public static LocalDate getFirstDayOfCurrentMonth() {
        return LocalDate.now().withDayOfMonth(1);
    }

    public static LocalDate getLastDayOfCurrentMonth() {
        LocalDate today = LocalDate.now();
        return today.withDayOfMonth(today.lengthOfMonth());
    }

    public static List<String> getNamesForLastTwelveMonths(){
        List<String> monthNames = new ArrayList<>();
        int monthFrom = LocalDate.now().getMonthValue() + 1;
        for (int i = monthFrom; i < monthFrom + 12; i++) {
            int currentMonthNumber = i > 12 ? i % 12 : i;

            String monthName = Month.of(currentMonthNumber).name();
            monthNames.add(monthName);
        }
        return monthNames;
    }
}
