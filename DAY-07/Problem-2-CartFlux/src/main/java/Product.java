package com.cartflux;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    
    private String productName;
    private String sku;
    
    public Product() {}
    public Product(String productName, String sku) {
        this.productName = productName;
        this.sku = sku;
    }
    
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getSku() { return sku; }
}
