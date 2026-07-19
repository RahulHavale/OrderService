package com.example.OrderService;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrder(Long id);

    void updateOrder(Long id,OrderRequest request);

    void deleteOrder(Long id);
}
