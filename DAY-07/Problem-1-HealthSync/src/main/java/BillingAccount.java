package com.healthsync;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "billing_account")
public class BillingAccount {
    @Id
    private Long patientId;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "patient_id")
    private Patient patient;
    
    private BigDecimal currentBalance;
    
    public BillingAccount() {}
    public BillingAccount(Patient patient, BigDecimal currentBalance) {
        this.patient = patient;
        this.currentBalance = currentBalance;
        this.patientId = patient.getPatientId();
    }
    
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    
    public BigDecimal getCurrentBalance() { return currentBalance; }
    public void setCurrentBalance(BigDecimal currentBalance) { this.currentBalance = currentBalance; }
}
