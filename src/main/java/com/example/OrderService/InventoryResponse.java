package com.example.OrderService;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InventoryResponse {
    private Long inventoryId;

    private Long productId;

    private Integer availableStock;

    private Integer reservedStock;

}
