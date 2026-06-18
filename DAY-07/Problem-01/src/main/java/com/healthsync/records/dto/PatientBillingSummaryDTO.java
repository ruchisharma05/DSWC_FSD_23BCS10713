package com.healthsync.records.dto;

import java.math.BigDecimal;

public class PatientBillingSummaryDTO {

    private final Long patientId;
    private final String patientName;
    private final BigDecimal balance;
    private final long medicalRecordCount;

    public PatientBillingSummaryDTO(Long patientId, String patientName, BigDecimal balance, long medicalRecordCount) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.balance = balance;
        this.medicalRecordCount = medicalRecordCount;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public long getMedicalRecordCount() {
        return medicalRecordCount;
    }
}
