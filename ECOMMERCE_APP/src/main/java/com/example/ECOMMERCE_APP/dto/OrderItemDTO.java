package com.example.ECOMMERCE_APP.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private String productName;
    private Integer quantity;
    private Double priceAtPurchase;
}