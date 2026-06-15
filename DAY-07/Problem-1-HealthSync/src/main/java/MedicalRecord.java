package com.healthsync;

import jakarta.persistence.*;

@Entity
@Table(name = "medical_record")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "record_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("MEDICAL_RECORD")
public abstract class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;
    
    // Inverse side of bidirectional relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    private String recordDate;
    
    // Constructors
    public MedicalRecord() {}
    public MedicalRecord(Patient patient, String recordDate) {
        this.patient = patient;
        this.recordDate = recordDate;
    }
    
    // Getters & Setters
    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    
    public String getRecordDate() { return recordDate; }
    public void setRecordDate(String recordDate) { this.recordDate = recordDate; }
    
    public abstract String getRecordType();
}
