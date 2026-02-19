package com.example.ECOMMERCE_APP.Repository;

import com.example.ECOMMERCE_APP.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Basic CRUD for categories
}