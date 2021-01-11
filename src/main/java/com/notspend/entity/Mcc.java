package com.notspend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(of = "mccId")
@Table(name = "mcc")
public class Mcc {

    @Id
    @Column(name = "mcc_id")
    private int mccId;

    @Column(name = "description")
    private String description;

    @Column(name = "category_name")
    private String categoryName;

}
