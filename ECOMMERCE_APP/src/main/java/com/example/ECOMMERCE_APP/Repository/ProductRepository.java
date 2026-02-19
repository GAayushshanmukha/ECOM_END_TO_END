package com.example.ECOMMERCE_APP.Repository;

import com.example.ECOMMERCE_APP.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom query to find products by their category
    List<Product> findByCategoryId(Long categoryId);

    // Case-insensitive search for product names
    List<Product> findByNameContainingIgnoreCase(String name);
}