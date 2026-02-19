package com.example.ECOMMERCE_APP.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long orderId;
    private Long amount;   // Remember: $10.00 = 1000
    private String currency; // "usd", "inr", etc.
}