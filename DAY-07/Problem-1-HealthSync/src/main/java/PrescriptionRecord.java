package com.healthsync;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PRESCRIPTION")
public class PrescriptionRecord extends MedicalRecord {
    private String medicationName;
    private String dosage;
    
    public PrescriptionRecord() {}
    public PrescriptionRecord(Patient patient, String recordDate, String medicationName, String dosage) {
        super(patient, recordDate);
        this.medicationName = medicationName;
        this.dosage = dosage;
    }
    
    public String getMedicationName() { return medicationName; }
    public void setMedicationName(String medicationName) { this.medicationName = medicationName; }
    
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    
    @Override
    public String getRecordType() { return "PRESCRIPTION"; }
}
