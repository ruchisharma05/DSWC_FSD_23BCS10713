package com.healthsync;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    // JPQL query with constructor expression returning DTO
    @Query("SELECT new com.healthsync.PatientBillingSummaryDTO(" +
           "p.patientName, CAST(m.record_type AS string), ba.currentBalance) " +
           "FROM Patient p " +
           "JOIN p.medicalRecords m " +
           "JOIN p.billingAccount ba " +
           "WHERE ba.currentBalance > :minBalance " +
           "ORDER BY p.patientName, ba.currentBalance DESC")
    List<PatientBillingSummaryDTO> findBillingSummary(@Param("minBalance") BigDecimal minBalance);
    
    // Derived query: Find patients with balance > amount and having specific prescription
    @Query("SELECT p FROM Patient p " +
           "WHERE EXISTS (SELECT 1 FROM p.billingAccount ba WHERE ba.currentBalance > :minBalance) " +
           "AND EXISTS (SELECT 1 FROM PrescriptionRecord pr WHERE pr.patient = p AND pr.medicationName = :medicationName)")
    List<Patient> findPatientsByBalanceAndMedication(
        @Param("minBalance") BigDecimal minBalance,
        @Param("medicationName") String medicationName
    );
}
