package com.example.OrderService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryRequest {

    private Long productId;

    private Integer availableStock;

    private Integer reservedStock;
}
