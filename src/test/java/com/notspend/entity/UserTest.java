package com.notspend.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    private final User user = new User();

    @Test
    void addExpence() {
        Expense expense = new Expense();
        user.addExpence(expense);
        assertEquals(user, expense.getUser());
    }

    @Test
    void addAccount() {
        Account account = new Account();
        user.addAccount(account);
        assertEquals(user, account.getUser());
    }
}