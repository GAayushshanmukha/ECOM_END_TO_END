package com.example.ECOMMERCE_APP.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;
    private Double totalAmount;
    private String status; // e.g., PENDING, CONFIRMED
    // Add this field to your Order class
    private String stripePaymentIntentId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Use "mappedBy" to link to the 'order' field in OrderResponse.java
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> items;

    // Use "mappedBy" to link to the 'order' field in Payment.java
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;
}