package com.healthsync.records.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;

@Entity
public class BillingAccount {

    @Id
    private Long patientId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private BigDecimal balance;

    protected BillingAccount() {
    }

    public BillingAccount(BigDecimal balance) {
        this.balance = balance;
    }

    void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Patient getPatient() {
        return patient;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
