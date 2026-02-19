package com.example.ECOMMERCE_APP.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String paymentMethod; // e.g., STRIPE, PAYPAL, CARD
    private String transactionId; // ID returned by the payment provider
    private Double amount;
    private String status; // PENDING, COMPLETED, FAILED

    private LocalDateTime paymentDate;
}