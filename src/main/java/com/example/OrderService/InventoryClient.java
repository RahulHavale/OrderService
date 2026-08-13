package com.example.OrderService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "InventoryService")
public interface InventoryClient {
    @GetMapping("/api/v1/inventory/product/{productId}")
    InventoryResponse getInventoryByProductId(
            @PathVariable Long productId);

    @PutMapping("/api/v1/inventory/reduce-stock/{productId}")
    void reduceStock(
            @PathVariable("productId") Long productId,
            @RequestParam Integer quantity
    );
}
