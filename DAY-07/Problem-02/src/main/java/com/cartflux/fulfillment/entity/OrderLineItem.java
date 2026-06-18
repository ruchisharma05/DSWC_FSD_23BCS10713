package com.cartflux.fulfillment.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class OrderLineItem {

    @EmbeddedId
    private OrderLineItemId id = new OrderLineItemId();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private PurchaseOrder order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private BigDecimal lockedPrice;

    protected OrderLineItem() {
    }

    public OrderLineItem(PurchaseOrder order, Product product, int quantity, BigDecimal lockedPrice) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.lockedPrice = lockedPrice;
    }

    public OrderLineItemId getId() {
        return id;
    }

    public PurchaseOrder getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getLockedPrice() {
        return lockedPrice;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OrderLineItem that)) {
            return false;
        }
        return Objects.equals(order, that.order) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}
