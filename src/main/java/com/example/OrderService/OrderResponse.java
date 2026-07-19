package com.example.OrderService;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderResponse {

    private Long orderId;

    private Long customerId;

    private Long productId;

    private Integer quantity;

    private Double totalAmount;

    private String status;

    private LocalDate createdDate;

    private LocalDate updatedDate;
}