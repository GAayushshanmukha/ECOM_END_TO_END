package com.example.ECOMMERCE_APP.Controller;

import com.example.ECOMMERCE_APP.Service.UserService;
import com.example.ECOMMERCE_APP.config.JwtUtils;
import com.example.ECOMMERCE_APP.dto.AuthResponse;
import com.example.ECOMMERCE_APP.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AuthController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        try {
            // 1. Authenticate the user
            User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

            // 2. Generate the token WITH the role (using user.getRole())
            // This matches the new signature: generateToken(String email, String role)
            String token = jwtUtils.generateToken(user.getEmail(), user.getRole());

            // 3. Return the response
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}