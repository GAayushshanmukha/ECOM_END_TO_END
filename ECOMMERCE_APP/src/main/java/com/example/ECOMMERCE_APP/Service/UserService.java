package com.example.ECOMMERCE_APP.Service;

import com.example.ECOMMERCE_APP.entity.User;
import com.example.ECOMMERCE_APP.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // Added this
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Added this

    // Constructor Injection for both Repository and Encoder
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use!");
        }

        // 1. Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 2. Default role (if not already set)
        if (user.getRole() == null) {
            user.setRole("ROLE_CUSTOMER");
        }

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // 3. Use matches() to compare the plain-text password with the hash in DB
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }
}