package com.cartflux;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class OrderLineItemId implements Serializable {
    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "product_id")
    private Long productId;
    
    public OrderLineItemId() {}
    public OrderLineItemId(Long orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
    
    public Long getOrderId() { return orderId; }
    public Long getProductId() { return productId; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineItemId that = (OrderLineItemId) o;
        return orderId.equals(that.orderId) && productId.equals(that.productId);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(orderId, productId);
    }
}
