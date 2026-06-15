package com.cartflux;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    // JOIN FETCH to solve N+1 problem
    @Query("SELECT DISTINCT po FROM PurchaseOrder po " +
           "LEFT JOIN FETCH po.lineItems li " +
           "LEFT JOIN FETCH li.product " +
           "WHERE po.status = 'PENDING'")
    List<PurchaseOrder> findPendingOrdersWithItems();
    
    // Derived query: Find orders by date range and customer email domain
    @Query("SELECT po FROM PurchaseOrder po " +
           "WHERE po.customerEmail LIKE CONCAT('%', :emailDomain) " +
           "ORDER BY po.orderId DESC")
    List<PurchaseOrder> findOrdersByEmailDomain(@Param("emailDomain") String emailDomain);
}
