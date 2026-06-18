package com.corpmatrix.hr.entity;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class FullTimeEmployee extends Employee {

    private BigDecimal annualSalary;

    protected FullTimeEmployee() {
    }

    public FullTimeEmployee(String firstName, String lastName, String department, BigDecimal annualSalary) {
        super(firstName, lastName, department);
        this.annualSalary = annualSalary;
    }

    public BigDecimal getAnnualSalary() {
        return annualSalary;
    }
}
