package com.example.ECOMMERCE_APP.Repository;

import com.example.ECOMMERCE_APP.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Crucial for login: finding a user by their unique email
    Optional<User> findByEmail(String email);
}