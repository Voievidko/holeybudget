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

    @Column(name = "number")
    private Integer number;

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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(name, currency.name) &&
                Objects.equals(code, currency.code) &&
                Objects.equals(number, currency.number) &&
                Objects.equals(symbol, currency.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code, number, symbol);
    }
}
