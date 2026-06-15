package com.cartflux;

import jakarta.persistence.*;

@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    
    private String status;
    private String customerEmail;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private java.util.List<OrderLineItem> lineItems;
    
    public PurchaseOrder() {}
    public PurchaseOrder(String status, String customerEmail) {
        this.status = status;
        this.customerEmail = customerEmail;
    }
    
    public Long getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public String getCustomerEmail() { return customerEmail; }
    public java.util.List<OrderLineItem> getLineItems() { return lineItems; }
    public void setLineItems(java.util.List<OrderLineItem> lineItems) { this.lineItems = lineItems; }
}
