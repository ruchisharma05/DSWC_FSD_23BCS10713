package com.corpmatrix.hr.entity;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class Contractor extends Employee {

    private BigDecimal hourlyRate;

    private String vendorName;

    protected Contractor() {
    }

    public Contractor(String firstName, String lastName, String department, BigDecimal hourlyRate, String vendorName) {
        super(firstName, lastName, department);
        this.hourlyRate = hourlyRate;
        this.vendorName = vendorName;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public String getVendorName() {
        return vendorName;
    }
}
