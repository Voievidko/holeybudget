package com.yourfounds.util;

import com.yourfounds.entity.Account;
import com.yourfounds.entity.Expense;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Calculation {
    public static Double exspenseSum (List<Expense> expenses){
        return expenses.stream().map(a -> a.getSum()).reduce(0d, (a, b) -> a + b);
    }

    public static Double accountSum (List<Account> account){
        return account.stream().map(a -> a.getSummary()).reduce(0d, (a, b) -> a + b);
    }

    public static Date getStartOfCurrentMonth() {
        Date begining;

        Calendar calendar = getCalendarForNow();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        setTimeToBeginningOfDay(calendar);
        begining = calendar.getTime();
        return begining;
    }

    public static Date getEndOfCurrentMonth() {
        Date end;
        Calendar calendar = getCalendarForNow();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setTimeToEndofDay(calendar);
        end = calendar.getTime();
        return end;
    }

    private static Calendar getCalendarForNow() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    private static void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setTimeToEndofDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }
}
