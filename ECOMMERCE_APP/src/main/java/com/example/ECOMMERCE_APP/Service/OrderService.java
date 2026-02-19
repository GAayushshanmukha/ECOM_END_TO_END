package com.example.ECOMMERCE_APP.Service;

import com.example.ECOMMERCE_APP.Repository.CartRepository;
import com.example.ECOMMERCE_APP.Repository.OrderRepository;
import com.example.ECOMMERCE_APP.Repository.ProductRepository;
import com.example.ECOMMERCE_APP.Repository.UserRepository;
import com.example.ECOMMERCE_APP.dto.OrderItemDTO;
import com.example.ECOMMERCE_APP.dto.OrderResponse;
import com.example.ECOMMERCE_APP.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

// IMPORTANT: These were missing!
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        CartRepository cartRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderResponse placeOrder(Long userId) { // Changed return type to DTO
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setStatus("PLACED");

        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            OrderItem oi = new OrderItem();
            oi.setProduct(cartItem.getProduct());
            oi.setQuantity(cartItem.getQuantity());
            oi.setPriceAtPurchase(cartItem.getProduct().getPrice());
            oi.setOrder(order);

            Product p = cartItem.getProduct();
            p.setStockQuantity(p.getStockQuantity() - cartItem.getQuantity());
            productRepository.saveAndFlush(p);

            return oi;
        }).collect(Collectors.toList());

        order.setItems(orderItems);

        double total = orderItems.stream()
                .mapToDouble(i -> i.getPriceAtPurchase() * i.getQuantity())
                .sum();
        order.setTotalAmount(total);

        Order savedOrder = orderRepository.saveAndFlush(order);

        cart.getItems().clear();
        cartRepository.saveAndFlush(cart);

        // --- STEP 3: MAPPING ENTITY TO DTO ---
        OrderResponse response = new OrderResponse();
        response.setOrderNumber(savedOrder.getOrderNumber());
        response.setStatus(savedOrder.getStatus());
        response.setTotalAmount(savedOrder.getTotalAmount());

        // Convert the list of OrderItem (Entities) to OrderItemDTO (Data Objects)
        List<OrderItemDTO> itemDTOs = savedOrder.getItems().stream().map(item -> {
            OrderItemDTO dto = new OrderItemDTO();
            dto.setProductName(item.getProduct().getName());
            dto.setQuantity(item.getQuantity());
            dto.setPriceAtPurchase(item.getPriceAtPurchase());
            return dto;
        }).collect(Collectors.toList());

        response.setItems(itemDTOs);

        return response; // Returning the clean, loop-free object
    }
}