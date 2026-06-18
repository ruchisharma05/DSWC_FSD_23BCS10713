package com.healthsync.records.repository;

import com.healthsync.records.dto.PatientBillingSummaryDTO;
import com.healthsync.records.entity.BillingAccount;
import com.healthsync.records.entity.LabResultRecord;
import com.healthsync.records.entity.MedicalRecord;
import com.healthsync.records.entity.Patient;
import com.healthsync.records.entity.PrescriptionRecord;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldPersistSingleTableRecordsAndDeleteBillingAndRecordsWithPatient() {
        Patient patient = new Patient("Anaya Kapoor");
        patient.setBillingAccount(new BillingAccount(new BigDecimal("1450.75")));
        patient.addMedicalRecord(new PrescriptionRecord("Migraine treatment", LocalDate.now(), "Sumatriptan", "50mg"));
        patient.addMedicalRecord(new LabResultRecord("Blood panel", LocalDate.now(), "CBC", "Normal"));

        Patient savedPatient = patientRepository.saveAndFlush(patient);
        Long patientId = savedPatient.getId();

        entityManager.clear();

        List<MedicalRecord> storedRecords = entityManager
                .createQuery("select mr from MedicalRecord mr where mr.patient.id = :patientId", MedicalRecord.class)
                .setParameter("patientId", patientId)
                .getResultList();

        assertEquals(2, storedRecords.size());
        assertTrue(storedRecords.stream().anyMatch(PrescriptionRecord.class::isInstance));
        assertTrue(storedRecords.stream().anyMatch(LabResultRecord.class::isInstance));

        patientRepository.deleteById(patientId);
        patientRepository.flush();

        Long medicalRecordCount = entityManager
                .createQuery("select count(mr) from MedicalRecord mr", Long.class)
                .getSingleResult();
        Long billingAccountCount = entityManager
                .createQuery("select count(ba) from BillingAccount ba", Long.class)
                .getSingleResult();

        assertEquals(0L, medicalRecordCount);
        assertEquals(0L, billingAccountCount);
    }

    @Test
    void shouldReturnPatientBillingSummaryDtoFromConstructorExpressionQuery() {
        Patient patient = new Patient("Kabir Mehta");
        patient.setBillingAccount(new BillingAccount(new BigDecimal("980.00")));
        patient.addMedicalRecord(new PrescriptionRecord("Infection control", LocalDate.now(), "Azithromycin", "500mg"));
        patient.addMedicalRecord(new LabResultRecord("Culture report", LocalDate.now(), "Urine Culture", "Pending"));

        patientRepository.saveAndFlush(patient);

        List<PatientBillingSummaryDTO> summaries = patientRepository.findPatientBillingSummaries();

        assertEquals(1, summaries.size());
        PatientBillingSummaryDTO summary = summaries.getFirst();
        assertEquals("Kabir Mehta", summary.getPatientName());
        assertEquals(new BigDecimal("980.00"), summary.getBalance());
        assertEquals(2L, summary.getMedicalRecordCount());
    }

    @Test
    void shouldFindPatientsByBalanceAndPrescriptionMedicationUsingDerivedQuery() {
        Patient matchingPatient = new Patient("Ishita Rao");
        matchingPatient.setBillingAccount(new BillingAccount(new BigDecimal("2100.00")));
        matchingPatient.addMedicalRecord(new PrescriptionRecord("Cardiac medication", LocalDate.now(), "Metoprolol", "25mg"));
        matchingPatient.addMedicalRecord(new LabResultRecord("Electrolyte test", LocalDate.now(), "Potassium", "Stable"));

        Patient nonMatchingPatient = new Patient("Arjun Bedi");
        nonMatchingPatient.setBillingAccount(new BillingAccount(new BigDecimal("900.00")));
        nonMatchingPatient.addMedicalRecord(new PrescriptionRecord("Pain management", LocalDate.now(), "Ibuprofen", "400mg"));

        patientRepository.saveAllAndFlush(List.of(matchingPatient, nonMatchingPatient));

        List<Patient> results = patientRepository
                .findDistinctByBillingAccountBalanceGreaterThanAndPrescriptionRecordsMedicationNameContainingIgnoreCase(
                        new BigDecimal("1000.00"),
                        "meto"
                );

        assertEquals(1, results.size());
        assertEquals("Ishita Rao", results.getFirst().getFullName());
        assertInstanceOf(Patient.class, results.getFirst());
    }
}
