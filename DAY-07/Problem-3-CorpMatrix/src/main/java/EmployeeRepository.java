package com.corpmatrix;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // GROUP BY with COUNT() aggregate function
    @Query("SELECT new com.corpmatrix.ManagerSpanDTO(" +
           "CONCAT(m.firstName, ' ', m.lastName), COUNT(e)) " +
           "FROM Employee e " +
           "INNER JOIN e.manager m " +
           "GROUP BY m.employeeId, m.firstName, m.lastName " +
           "ORDER BY COUNT(e) DESC")
    List<ManagerSpanDTO> findManagerSpans();
    
    // Derived query: Find employees by manager firstName and department
    @Query("SELECT e FROM Employee e " +
           "WHERE e.manager.firstName = :managerFirstName " +
           "AND e.department = :department")
    List<Employee> findEmployeesByManagerAndDepartment(
        @Param("managerFirstName") String managerFirstName,
        @Param("department") String department
    );
}
