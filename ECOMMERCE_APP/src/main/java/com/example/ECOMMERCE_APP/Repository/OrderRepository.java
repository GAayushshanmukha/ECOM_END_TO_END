package com.example.ECOMMERCE_APP.Repository;

import com.example.ECOMMERCE_APP.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    Optional<Order> findByOrderNumber(String orderNumber);

    // New method to find the order using the ID provided by Stripe
    Optional<Order> findByStripePaymentIntentId(String stripePaymentIntentId);
}