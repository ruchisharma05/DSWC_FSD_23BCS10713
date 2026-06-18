package com.healthsync.records.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("LAB_RESULT")
public class LabResultRecord extends MedicalRecord {

    private String testName;

    private String resultValue;

    protected LabResultRecord() {
    }

    public LabResultRecord(String summary, LocalDate recordDate, String testName, String resultValue) {
        super(summary, recordDate);
        this.testName = testName;
        this.resultValue = resultValue;
    }

    public String getTestName() {
        return testName;
    }

    public String getResultValue() {
        return resultValue;
    }
}
