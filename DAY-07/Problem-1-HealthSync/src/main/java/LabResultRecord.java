package com.healthsync;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("LAB_RESULT")
public class LabResultRecord extends MedicalRecord {
    private String testName;
    private String resultValue;
    
    public LabResultRecord() {}
    public LabResultRecord(Patient patient, String recordDate, String testName, String resultValue) {
        super(patient, recordDate);
        this.testName = testName;
        this.resultValue = resultValue;
    }
    
    public String getTestName() { return testName; }
    public void setTestName(String testName) { this.testName = testName; }
    
    public String getResultValue() { return resultValue; }
    public void setResultValue(String resultValue) { this.resultValue = resultValue; }
    
    @Override
    public String getRecordType() { return "LAB_RESULT"; }
}
