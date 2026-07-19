package com.example.OrderService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name="InventoryService",
        url="http://localhost:8083"
)
public interface InventoryClient {
    @GetMapping("/api/v1/inventory/{id}")
    InventoryResponse getInventory(
         @PathVariable Long id
    );

    @PutMapping("/api/v1/inventory/reduce-stock/{productId}")
    void reduceStock(
            @PathVariable("productId") Long productId,
            @RequestParam Integer quantity
    );
}
