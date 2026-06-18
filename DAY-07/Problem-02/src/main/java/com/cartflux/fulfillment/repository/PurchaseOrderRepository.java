package com.cartflux.fulfillment.repository;

import com.cartflux.fulfillment.entity.OrderStatus;
import com.cartflux.fulfillment.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    @Query("""
            select distinct po
            from PurchaseOrder po
            join fetch po.lineItems li
            join fetch li.product p
            where po.status = :status
            order by po.id
            """)
    List<PurchaseOrder> findOrdersByStatusWithLineItemsAndProducts(OrderStatus status);

    List<PurchaseOrder> findByPlacedAtBetweenAndCustomerEmailEndingWith(Instant start, Instant end, String domain);
}
