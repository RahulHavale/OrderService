package com.example.OrderService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    private Long customerId;

    private Long productId;

    private Integer quantity;

    private Double totalAmount;

    private String paymentMethod;
}
