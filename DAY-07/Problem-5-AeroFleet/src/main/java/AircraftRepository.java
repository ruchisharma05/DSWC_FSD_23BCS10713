package com.aerofleet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    // JPQL query searching embedded collection with pagination
    @Query("SELECT a FROM Aircraft a " +
           "WHERE EXISTS (SELECT 1 FROM a.maintenanceLogs ml " +
           "              WHERE ml.actionType = :actionType AND ml.date >= :startDate)")
    Page<Aircraft> findAircraftByMaintenanceType(
        @Param("actionType") String actionType,
        @Param("startDate") String startDate,
        Pageable pageable
    );
    
    // Derived query: Find by modelName list and isGrounded flag
    List<Aircraft> findByModelNameInAndIsGroundedTrue(List<String> modelNames);
}
