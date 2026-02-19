package com.example.ECOMMERCE_APP.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderResponse {
    private String orderNumber;
    private String status;
    private Double totalAmount;
    private List<OrderItemDTO> items;
}