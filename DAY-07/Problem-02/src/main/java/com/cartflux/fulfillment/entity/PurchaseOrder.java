package com.cartflux.fulfillment.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerEmail;

    private Instant placedAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderLineItem> lineItems = new LinkedHashSet<>();

    protected PurchaseOrder() {
    }

    public PurchaseOrder(String customerEmail, Instant placedAt, OrderStatus status) {
        this.customerEmail = customerEmail;
        this.placedAt = placedAt;
        this.status = status;
    }

    public void addLineItem(Product product, int quantity, BigDecimal lockedPrice) {
        OrderLineItem lineItem = new OrderLineItem(this, product, quantity, lockedPrice);
        lineItems.add(lineItem);
    }

    public Long getId() {
        return id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Instant getPlacedAt() {
        return placedAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Set<OrderLineItem> getLineItems() {
        return lineItems;
    }
}
