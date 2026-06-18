package com.healthsync.records.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("PRESCRIPTION")
public class PrescriptionRecord extends MedicalRecord {

    private String medicationName;

    private String dosage;

    protected PrescriptionRecord() {
    }

    public PrescriptionRecord(String summary, LocalDate recordDate, String medicationName, String dosage) {
        super(summary, recordDate);
        this.medicationName = medicationName;
        this.dosage = dosage;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getDosage() {
        return dosage;
    }
}
