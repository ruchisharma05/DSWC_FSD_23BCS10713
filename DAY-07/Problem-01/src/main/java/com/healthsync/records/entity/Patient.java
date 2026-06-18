package com.healthsync.records.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    @OneToMany(mappedBy = "patient")
    private List<PrescriptionRecord> prescriptionRecords = new ArrayList<>();

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private BillingAccount billingAccount;

    protected Patient() {
    }

    public Patient(String fullName) {
        this.fullName = fullName;
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecords.add(medicalRecord);
        medicalRecord.setPatient(this);

        if (medicalRecord instanceof PrescriptionRecord prescriptionRecord && !prescriptionRecords.contains(prescriptionRecord)) {
            prescriptionRecords.add(prescriptionRecord);
        }
    }

    public void setBillingAccount(BillingAccount billingAccount) {
        this.billingAccount = billingAccount;
        billingAccount.setPatient(this);
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public List<PrescriptionRecord> getPrescriptionRecords() {
        return prescriptionRecords;
    }

    public BillingAccount getBillingAccount() {
        return billingAccount;
    }
}
