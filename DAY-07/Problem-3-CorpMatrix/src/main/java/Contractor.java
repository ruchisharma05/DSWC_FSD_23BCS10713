package com.corpmatrix;

import jakarta.persistence.*;

@Entity
@Table(name = "contractor")
@DiscriminatorValue("CONTRACTOR")
public class Contractor extends Employee {
    private BigDecimal hourlyRate;
    private String agency;
    
    public Contractor() {}
    public Contractor(String firstName, String lastName, String department, BigDecimal hourlyRate) {
        super(firstName, lastName, department);
        this.hourlyRate = hourlyRate;
    }
    
    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }
    public String getAgency() { return agency; }
    public void setAgency(String agency) { this.agency = agency; }
}
