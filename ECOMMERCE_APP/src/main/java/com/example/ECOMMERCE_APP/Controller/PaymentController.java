package com.example.ECOMMERCE_APP.Controller;

import com.example.ECOMMERCE_APP.Service.PaymentService;
import com.example.ECOMMERCE_APP.dto.PaymentRequest;
import com.example.ECOMMERCE_APP.entity.Order;
import com.example.ECOMMERCE_APP.Repository.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.ApiResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/create-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentRequest request) {
        try {
            // 1. Create the intent via Stripe
            String clientSecret = paymentService.createPaymentIntent(request.getAmount(), request.getCurrency());

            // 2. Link the PaymentIntent ID to our Order in the database
            // The PaymentIntent ID is the first part of the secret (before _secret_)
            String paymentIntentId = clientSecret.split("_secret")[0];

            Optional<Order> orderOpt = orderRepository.findById(request.getOrderId());
            if (orderOpt.isPresent()) {
                Order order = orderOpt.get();
                order.setStripePaymentIntentId(paymentIntentId);
                orderRepository.save(order);
            }

            return ResponseEntity.ok().body("{\"clientSecret\":\"" + clientSecret + "\"}");
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            // In test mode without the Stripe CLI, we parse the raw event
            Event event = ApiResource.GSON.fromJson(payload, Event.class);

            // Handle the "payment_intent.succeeded" event
            if ("payment_intent.succeeded".equals(event.getType())) {
                PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer().getObject().get();

                // 3. Find the order by the Stripe ID and update status
                Optional<Order> orderOpt = orderRepository.findByStripePaymentIntentId(intent.getId());

                if (orderOpt.isPresent()) {
                    Order order = orderOpt.get();
                    order.setStatus("PAID");
                    orderRepository.save(order);
                    System.out.println("Order " + order.getId() + " marked as PAID!");
                }
            }

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            System.err.println("Webhook error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook error");
        }
    }
}