package com.notspend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpenseTest {

    private final Expense baseExpense = new Expense();

    @BeforeEach
    void setUp() {
        LocalDate date = LocalDate.of(2020, 2, 20);
        LocalTime time = LocalTime.of(11, 11, 11);
        baseExpense.setDate(date);
        baseExpense.setTime(time);
    }

    @Test
    void compareToEarlierExpenseReturnsPositive() {
        Expense expenseToCompare = new Expense();
        expenseToCompare.setDate(LocalDate.of(2020, 2, 12));

        assertThat(baseExpense.compareTo(expenseToCompare)).isGreaterThan(0);
    }

    @Test
    void compareToLaterExpenseReturnsNegative() {
        Expense expenseToCompare = new Expense();
        expenseToCompare.setDate(LocalDate.of(2020, 2, 22));

        assertThat(baseExpense.compareTo(expenseToCompare)).isLessThan(0);
    }

    @Test
    void compareToSelfReturnsZero() {
        assertThat(baseExpense.compareTo(baseExpense)).isZero();
    }

    @Test
    void compareToNull() {
        assertThrows(NullPointerException.class, () -> baseExpense.compareTo(null));
    }

    @Test
    void compareToSameDateExpenseButEarlierTimeReturnsPositive() {
        Expense expenseToCompare = new Expense();
        expenseToCompare.setDate(LocalDate.of(2020, 2, 20));
        expenseToCompare.setTime(LocalTime.of(11, 0, 0));

        assertThat(baseExpense.compareTo(expenseToCompare)).isGreaterThan(0);
    }

    @Test
    void compareToSameDateExpenseButLaterTimeReturnsNegative() {
        Expense expenseToCompare = new Expense();
        expenseToCompare.setDate(LocalDate.of(2020, 2, 20));
        expenseToCompare.setTime(LocalTime.of(11, 11, 12));

        assertThat(baseExpense.compareTo(expenseToCompare)).isLessThan(0);
    }

    @Test
    void compareToSameDateReturnsZero() {
        Expense expenseToCompare = new Expense();
        expenseToCompare.setDate(LocalDate.of(2020, 2, 20));
        expenseToCompare.setTime(LocalTime.of(11, 11, 11));

        assertThat(baseExpense.compareTo(expenseToCompare)).isZero();
    }
}