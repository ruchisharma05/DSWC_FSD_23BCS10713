package com.cartflux;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_line_item")
public class OrderLineItem {
    @EmbeddedId
    private OrderLineItemId id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private PurchaseOrder order;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
    
    private int quantity;
    private BigDecimal lockedPrice;
    
    public OrderLineItem() {}
    public OrderLineItem(PurchaseOrder order, Product product, int quantity, BigDecimal lockedPrice) {
        this.id = new OrderLineItemId(order.getOrderId(), product.getProductId());
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.lockedPrice = lockedPrice;
    }
    
    public OrderLineItemId getId() { return id; }
    public PurchaseOrder getOrder() { return order; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public BigDecimal getLockedPrice() { return lockedPrice; }
}
