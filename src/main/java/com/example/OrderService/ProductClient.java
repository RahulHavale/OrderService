package com.example.OrderService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name="ecommerce",
        url="http://localhost:8080"
)
public interface ProductClient {

    @GetMapping("/api/v1/search")
    ProductResponse getProduct(
            @RequestParam("id") Long id
    );
}
