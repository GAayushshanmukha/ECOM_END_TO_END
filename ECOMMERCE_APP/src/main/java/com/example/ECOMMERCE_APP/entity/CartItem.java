package com.example.ECOMMERCE_APP.entity;

import com.fasterxml.jackson.annotation.JsonBackReference; // Import this
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_items")
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @JsonBackReference // This stops the infinite JSON loop
    @ToString.Exclude  // This stops the infinite Lombok loop
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}