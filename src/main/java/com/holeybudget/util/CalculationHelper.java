package com.holeybudget.util;

import com.holeybudget.entity.Account;
import com.holeybudget.entity.Currency;
import com.holeybudget.entity.Expense;

import java.util.List;

public class CalculationHelper {

    private CalculationHelper() {
    }

    public static Double expenseSum(List<Expense> expenses, Currency defaultCurrency){
        Double sum = 0D;
        for (Expense expense : expenses){
            String currencyCode = expense.getCurrency().getCode();
            //TODO: replace hardcoded currency
            if (!currencyCode.equals(defaultCurrency.getCode())){
                Double currencyRate = CurrencyProcessor.getCurrencyRate(currencyCode, defaultCurrency.getCode());
                sum = sum + expense.getSum() * currencyRate;
            } else {
                sum += expense.getSum();
            }
        }
        return sum;
    }

    public static Double accountSum (List<Account> accounts, Currency defaultCurrency){
        Double sum = 0D;
        for (Account account : accounts){
            String currencyCode = account.getCurrency().getCode();
            if (!currencyCode.equals(defaultCurrency.getCode())){
                Double currencyRate = CurrencyProcessor.getCurrencyRate(currencyCode, defaultCurrency.getCode());
                sum = sum + account.getSummary() * currencyRate;
            } else {
                sum += account.getSummary();
            }
        }
        return sum;
    }
}
