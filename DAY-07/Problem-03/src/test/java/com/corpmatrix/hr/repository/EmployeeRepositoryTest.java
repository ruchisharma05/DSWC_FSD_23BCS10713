package com.corpmatrix.hr.repository;

import com.corpmatrix.hr.dto.ManagerSpanDTO;
import com.corpmatrix.hr.entity.Contractor;
import com.corpmatrix.hr.entity.Employee;
import com.corpmatrix.hr.entity.FullTimeEmployee;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldPersistJoinedEmployeesAndReturnManagerSpanDto() {
        FullTimeEmployee manager = new FullTimeEmployee("Priya", "Nair", "Engineering", new BigDecimal("185000"));
        FullTimeEmployee developer = new FullTimeEmployee("Neel", "Patel", "Engineering", new BigDecimal("125000"));
        Contractor analyst = new Contractor("Karan", "Singh", "Engineering", new BigDecimal("95"), "Acme Talent");

        manager.addDirectReport(developer);
        manager.addDirectReport(analyst);

        employeeRepository.saveAndFlush(manager);
        entityManager.clear();

        List<ManagerSpanDTO> spans = employeeRepository.findManagerSpans();
        List<Employee> employees = employeeRepository.findAll();

        assertEquals(1, spans.size());
        assertEquals("Priya Nair", spans.getFirst().getManagerName());
        assertEquals(2L, spans.getFirst().getDirectReportCount());
        assertTrue(employees.stream().anyMatch(FullTimeEmployee.class::isInstance));
        assertTrue(employees.stream().anyMatch(Contractor.class::isInstance));
    }

    @Test
    void shouldFindEmployeesByManagerFirstNameAndDepartment() {
        FullTimeEmployee manager = new FullTimeEmployee("Aisha", "Verma", "Operations", new BigDecimal("172000"));
        FullTimeEmployee operationsLead = new FullTimeEmployee("Rohan", "Das", "Operations", new BigDecimal("118000"));
        Contractor contractor = new Contractor("Mira", "Joshi", "Finance", new BigDecimal("120"), "NorthBridge");

        manager.addDirectReport(operationsLead);
        manager.addDirectReport(contractor);

        employeeRepository.saveAndFlush(manager);

        List<Employee> results = employeeRepository.findByManagerFirstNameAndDepartment("Aisha", "Operations");

        assertEquals(1, results.size());
        assertEquals("Rohan", results.getFirst().getFirstName());
        assertInstanceOf(FullTimeEmployee.class, results.getFirst());
    }
}
