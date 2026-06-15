package com.corpmatrix;

import jakarta.persistence.*;

@Entity
@Table(name = "full_time_employee")
@DiscriminatorValue("FULL_TIME")
public class FullTimeEmployee extends Employee {
    private BigDecimal salary;
    private String benefits;
    
    public FullTimeEmployee() {}
    public FullTimeEmployee(String firstName, String lastName, String department, BigDecimal salary) {
        super(firstName, lastName, department);
        this.salary = salary;
    }
    
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    public String getBenefits() { return benefits; }
    public void setBenefits(String benefits) { this.benefits = benefits; }
}
