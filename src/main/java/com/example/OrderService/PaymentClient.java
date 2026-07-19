package com.example.OrderService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name="PaymentService",
        url="http://localhost:8084"
)
public interface PaymentClient {

    @PostMapping("/api/v1/payments")
    PaymentResponse makePayment(@RequestBody PaymentRequest request);
}
