package com.healthsync;

import java.math.BigDecimal;

public class PatientBillingSummaryDTO {
    private String patientName;
    private String recordType;
    private BigDecimal currentBalance;
    
    public PatientBillingSummaryDTO(String patientName, String recordType, BigDecimal currentBalance) {
        this.patientName = patientName;
        this.recordType = recordType;
        this.currentBalance = currentBalance;
    }
    
    public String getPatientName() { return patientName; }
    public String getRecordType() { return recordType; }
    public BigDecimal getCurrentBalance() { return currentBalance; }
    
    @Override
    public String toString() {
        return String.format("PatientBillingSummaryDTO{patientName='%s', recordType='%s', currentBalance=%s}",
                patientName, recordType, currentBalance);
    }
}
