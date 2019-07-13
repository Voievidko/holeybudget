package com.notspend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "currency")
public class Currency {

    @Column(name = "name")
    private String name;

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "symbol")
    private String symbol;

    public Currency() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return Objects.equals(getName(), currency.getName()) &&
                Objects.equals(getCode(), currency.getCode()) &&
                Objects.equals(getSymbol(), currency.getSymbol());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCode(), getSymbol());
    }
}
