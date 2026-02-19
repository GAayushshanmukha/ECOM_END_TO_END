package com.example.ECOMMERCE_APP.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class PaymentService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        // Initialize the Stripe SDK with your secret key
        Stripe.apiKey = stripeSecretKey;
    }

    public String createPaymentIntent(Long amount, String currency) throws StripeException {
        // Stripe expects the amount in CENTS (e.g., $10.00 = 1000)
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                // In modern Stripe, we enable automatic payment methods (Card, Apple Pay, etc.)
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .build()
                )
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        // This Client Secret is what the frontend needs to complete the payment
        return intent.getClientSecret();
    }

    public String getPaymentStatus(String paymentIntentId) throws StripeException {
        PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
        return intent.getStatus(); // This will return "requires_payment_method", "succeeded", etc.
    }
}