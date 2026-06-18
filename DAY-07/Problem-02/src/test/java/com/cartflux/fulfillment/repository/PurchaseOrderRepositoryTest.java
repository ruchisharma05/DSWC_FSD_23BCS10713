package com.cartflux.fulfillment.repository;

import com.cartflux.fulfillment.entity.OrderStatus;
import com.cartflux.fulfillment.entity.Product;
import com.cartflux.fulfillment.entity.PurchaseOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnitUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PurchaseOrderRepositoryTest {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void shouldFetchPendingOrdersWithLineItemsAndProductsInOneQueryShape() {
        Product keyboard = new Product("KB-01", "Mechanical Keyboard", new BigDecimal("89.99"));
        Product mouse = new Product("MS-09", "Gaming Mouse", new BigDecimal("54.99"));
        entityManager.persist(keyboard);
        entityManager.persist(mouse);

        PurchaseOrder pendingOrder = new PurchaseOrder("ops@cartflux.com", Instant.now(), OrderStatus.PENDING);
        pendingOrder.addLineItem(keyboard, 2, new BigDecimal("79.99"));
        pendingOrder.addLineItem(mouse, 1, new BigDecimal("49.99"));

        PurchaseOrder fulfilledOrder = new PurchaseOrder("archive@cartflux.com", Instant.now(), OrderStatus.FULFILLED);
        fulfilledOrder.addLineItem(mouse, 3, new BigDecimal("47.99"));

        purchaseOrderRepository.saveAllAndFlush(List.of(pendingOrder, fulfilledOrder));
        entityManager.clear();

        List<PurchaseOrder> pendingOrders = purchaseOrderRepository.findOrdersByStatusWithLineItemsAndProducts(OrderStatus.PENDING);
        PersistenceUnitUtil persistenceUnitUtil = entityManagerFactory.getPersistenceUnitUtil();

        assertEquals(1, pendingOrders.size());
        PurchaseOrder loadedOrder = pendingOrders.getFirst();
        assertEquals(2, loadedOrder.getLineItems().size());
        assertTrue(persistenceUnitUtil.isLoaded(loadedOrder, "lineItems"));
        assertTrue(loadedOrder.getLineItems().stream().allMatch(lineItem -> persistenceUnitUtil.isLoaded(lineItem, "product")));
    }

    @Test
    void shouldFindOrdersBetweenTimestampsWithCustomerDomain() {
        Product dock = new Product("DK-22", "USB-C Dock", new BigDecimal("159.99"));
        entityManager.persist(dock);

        Instant now = Instant.now();
        PurchaseOrder matchingOrder = new PurchaseOrder("buyer@enterprise.com", now.minus(2, ChronoUnit.HOURS), OrderStatus.PENDING);
        matchingOrder.addLineItem(dock, 1, new BigDecimal("149.99"));

        PurchaseOrder wrongDomainOrder = new PurchaseOrder("buyer@gmail.com", now.minus(1, ChronoUnit.HOURS), OrderStatus.PENDING);
        wrongDomainOrder.addLineItem(dock, 2, new BigDecimal("145.00"));

        PurchaseOrder outOfRangeOrder = new PurchaseOrder("late@enterprise.com", now.minus(3, ChronoUnit.DAYS), OrderStatus.PENDING);
        outOfRangeOrder.addLineItem(dock, 1, new BigDecimal("149.99"));

        purchaseOrderRepository.saveAllAndFlush(List.of(matchingOrder, wrongDomainOrder, outOfRangeOrder));

        List<PurchaseOrder> results = purchaseOrderRepository.findByPlacedAtBetweenAndCustomerEmailEndingWith(
                now.minus(1, ChronoUnit.DAYS),
                now,
                "@enterprise.com"
        );

        assertEquals(1, results.size());
        assertEquals("buyer@enterprise.com", results.getFirst().getCustomerEmail());
    }
}
