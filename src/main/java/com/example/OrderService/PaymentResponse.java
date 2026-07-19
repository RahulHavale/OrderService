package com.example.OrderService;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentResponse {

    private Long paymentId;

    private Long orderId;

    private Double amount;

    private String paymentMethod;

    private String paymentStatus;

    private String transactionId;

    private LocalDate paymentDate;

    private LocalDate updatedDate;
}
