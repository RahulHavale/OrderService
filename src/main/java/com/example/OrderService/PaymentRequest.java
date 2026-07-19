package com.example.OrderService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

    private Long orderId;

    private Double amount;

    private String paymentMethod;
}