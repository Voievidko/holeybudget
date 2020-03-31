package com.notspend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mcc")
public class Mcc {

    @Id
    @Column(name = "mcc_id")
    private int mccId;

    @Column(name = "description")
    private String description;

    @Column(name = "category_name")
    private String categoryName;

    public Mcc() {
    }

    public int getMccId() {
        return mccId;
    }

    public void setMccId(int mccId) {
        this.mccId = mccId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
