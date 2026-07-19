package com.example.OrderService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    private Long productId;
    private String name;
    private Double price;
    private Integer stock;
    private String category;
}
