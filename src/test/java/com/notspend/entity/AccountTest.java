package com.notspend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    private final Account account = new Account();

    @BeforeEach
    void setUp() {
        account.setSummary(120.53);
    }

    @Test
    void substract() {
        account.substract(20.53);
        assertEquals(Double.valueOf(100.00), account.getSummary());
    }

    @Test
    void plus() {
        account.plus(29.47);
        assertEquals(Double.valueOf(150.00), account.getSummary());
    }

    @Test
    void minus() {
        account.minus(20.53);
        assertEquals(Double.valueOf(100.00), account.getSummary());
    }
}