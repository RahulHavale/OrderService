package com.example.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request){

        OrderResponse order = service.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping
    public List<OrderResponse> getAllOrders(){

        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id){

        return service.getOrder(id);
    }

    @PutMapping("/{id}")
    public void updateOrder(@PathVariable Long id,
                            @RequestBody OrderRequest request){

        service.updateOrder(id,request);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id){

        service.deleteOrder(id);
    }
}
