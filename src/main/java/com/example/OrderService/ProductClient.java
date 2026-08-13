package com.example.OrderService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ecommerce")
public interface ProductClient {

    @GetMapping("/api/v1/products/{id}")
    ProductResponse getProduct(
            @PathVariable("id") Long id
    );
}
