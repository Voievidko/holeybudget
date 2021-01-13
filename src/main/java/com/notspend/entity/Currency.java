package com.notspend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@EqualsAndHashCode(of = {"code"})
@Data
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

}
