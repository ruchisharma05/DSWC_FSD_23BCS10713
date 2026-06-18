package com.corpmatrix.hr.repository;

import com.corpmatrix.hr.dto.ManagerSpanDTO;
import com.corpmatrix.hr.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("""
            select new com.corpmatrix.hr.dto.ManagerSpanDTO(
                concat(manager.firstName, ' ', manager.lastName),
                count(report)
            )
            from Employee manager
            inner join manager.directReports report
            group by manager.id, manager.firstName, manager.lastName
            order by manager.id
            """)
    List<ManagerSpanDTO> findManagerSpans();

    List<Employee> findByManagerFirstNameAndDepartment(String managerFirstName, String department);
}
