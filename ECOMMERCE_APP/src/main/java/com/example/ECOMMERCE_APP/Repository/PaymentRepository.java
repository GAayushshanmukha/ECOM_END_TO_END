package com.example.ECOMMERCE_APP.Repository;

import com.example.ECOMMERCE_APP.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Useful for tracking specific gateway transactions
    java.util.Optional<Payment> findByTransactionId(String transactionId);
}