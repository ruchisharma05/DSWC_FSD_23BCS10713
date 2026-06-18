package com.healthsync.records.repository;

import com.healthsync.records.dto.PatientBillingSummaryDTO;
import com.healthsync.records.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("""
            select new com.healthsync.records.dto.PatientBillingSummaryDTO(
                p.id,
                p.fullName,
                ba.balance,
                count(mr)
            )
            from Patient p
            join p.billingAccount ba
            join p.medicalRecords mr
            group by p.id, p.fullName, ba.balance
            order by p.id
            """)
    List<PatientBillingSummaryDTO> findPatientBillingSummaries();

    List<Patient> findDistinctByBillingAccountBalanceGreaterThanAndPrescriptionRecordsMedicationNameContainingIgnoreCase(
            BigDecimal balance,
            String medicationName
    );
}
