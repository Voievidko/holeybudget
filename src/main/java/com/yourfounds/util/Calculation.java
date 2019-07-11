package com.yourfounds.util;

import com.yourfounds.entity.Account;
import com.yourfounds.entity.Expense;
import com.yourfounds.util.currency.CurrencyProcessor;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Calculation {
    public static Double exspenseSum (List<Expense> expenses){
        Double sum = 0D;
        for (Expense expense : expenses){
            String currencyCode = expense.getCurrency().getCode();
            //TODO: replace hardcoded currency
            if (!currencyCode.equals("UAH")){
                Double currencyRate = CurrencyProcessor.getCurrency(currencyCode);
                sum = sum + expense.getSum() * currencyRate;
            } else {
                sum += expense.getSum();
            }
        }
        return sum;
    }

    public static Double accountSum (List<Account> accounts){
        Double sum = 0D;
        for (Account account : accounts){
            String currencyCode = account.getCurrency().getCode();
            //TODO: replace hardcoded currency
            if (!currencyCode.equals("UAH")){
                Double currencyRate = CurrencyProcessor.getCurrency(currencyCode);
                sum = sum + account.getSummary() * currencyRate;
            } else {
                sum += account.getSummary();
            }
        }
        return sum;
//        return account.stream().map(a -> a.getSummary()).reduce(0d, (a, b) -> a + b);
    }

    public static LocalDate getFirstDayOfCurrentMonth() {
        return LocalDate.now().withDayOfMonth(1);
    }

    public static LocalDate getLastDayOfCurrentMonth() {
        LocalDate today = LocalDate.now();
        return today.withDayOfMonth(today.lengthOfMonth());
    }
}
