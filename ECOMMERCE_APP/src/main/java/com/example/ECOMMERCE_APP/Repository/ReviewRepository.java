package com.example.ECOMMERCE_APP.Repository;

import com.example.ECOMMERCE_APP.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Requirement 7: Get all reviews for a specific product
    List<Review> findByProductId(Long productId);
}