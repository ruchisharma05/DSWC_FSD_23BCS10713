package com.healthsync;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    
    private String patientName;
    
    // Owning side of bidirectional relationship
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalRecord> medicalRecords;
    
    // One-To-One with BillingAccount, sharing the PK
    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    private BillingAccount billingAccount;
    
    // Constructors
    public Patient() {}
    public Patient(String patientName) { this.patientName = patientName; }
    
    // Getters & Setters
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    
    public List<MedicalRecord> getMedicalRecords() { return medicalRecords; }
    public void setMedicalRecords(List<MedicalRecord> medicalRecords) { this.medicalRecords = medicalRecords; }
    
    public BillingAccount getBillingAccount() { return billingAccount; }
    public void setBillingAccount(BillingAccount billingAccount) { this.billingAccount = billingAccount; }
}
