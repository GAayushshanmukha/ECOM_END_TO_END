package com.example.ECOMMERCE_APP.Repository;

import com.example.ECOMMERCE_APP.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}