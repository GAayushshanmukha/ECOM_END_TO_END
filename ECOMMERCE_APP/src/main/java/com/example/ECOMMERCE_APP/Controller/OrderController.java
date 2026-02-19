package com.example.ECOMMERCE_APP.Controller;

import com.example.ECOMMERCE_APP.Service.OrderService;
import com.example.ECOMMERCE_APP.dto.OrderResponse; // Corrected import to DTO
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    // Constructor Injection
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

        @PostMapping("/place")
        public ResponseEntity<OrderResponse> placeOrder(@RequestParam Long userId) {
            // No more try-catch! Just a clean, one-line execution.
            return ResponseEntity.ok(orderService.placeOrder(userId));
        }
    }
