package com.example.ECOMMERCE_APP.Repository;

import com.example.ECOMMERCE_APP.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Used to retrieve a specific user's persistent shopping cart
    Optional<Cart> findByUserId(Long userId);
}