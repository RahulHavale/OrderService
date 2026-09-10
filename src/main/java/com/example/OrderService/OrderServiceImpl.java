package com.example.OrderService;

import com.example.OrderService.exception.InsufficientStockException;
import com.example.OrderService.exception.OrderNotFoundException;
import com.example.OrderService.exception.PaymentFailedException;
import com.example.OrderService.exception.ProductNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final ModelMapper mapper;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    private final PaymentClient paymentClient;

    @Override
//    @CircuitBreaker(
//            name = "inventoryService",
//            fallbackMethod = "inventoryFallback"
//    )
//    @Retry(
//            name = "inventoryService",
//            fallbackMethod = "inventoryFallback"
//    )
    public OrderResponse createOrder(OrderRequest request) {

        ProductResponse product =
                productClient.getProduct(request.getProductId());

        if (product == null) {
            throw new ProductNotFoundException(
                    "Product not found with id : " + request.getProductId());
        }

        InventoryResponse inventory =
                inventoryClient.getInventoryByProductId(request.getProductId());

        if (inventory == null) {
            throw new InsufficientStockException(
                    "Inventory not found for product id : " + request.getProductId());
        }

        if (inventory.getAvailableStock() < request.getQuantity()) {
            throw new InsufficientStockException(
                    "Insufficient stock available.");
        }

//        PaymentRequest paymentRequest = new PaymentRequest();
//        paymentRequest.setAmount(product.getPrice() * request.getQuantity());
//        paymentRequest.setPaymentMethod(request.getPaymentMethod());
//        PaymentResponse paymentResponse =
//                paymentClient.makePayment(paymentRequest);
//
//        if (paymentResponse == null) {
//            throw new PaymentFailedException("Payment failed.");
//        }

        inventoryClient.reduceStock(
                request.getProductId(),
                request.getQuantity());

        OrderEntity entity = new OrderEntity();
        entity.setCustomerId(request.getCustomerId());
        entity.setProductId(request.getProductId());
        entity.setQuantity(request.getQuantity());
        entity.setTotalAmount(product.getPrice() * request.getQuantity());
        entity.setStatus("CONFIRMED");
        entity.setCreatedDate(LocalDate.now());
        entity.setUpdatedDate(LocalDate.now());

        repository.save(entity);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(entity.getOrderId());
        paymentRequest.setAmount(product.getPrice() * request.getQuantity());
        paymentRequest.setPaymentMethod(request.getPaymentMethod());
        PaymentResponse paymentResponse =
                paymentClient.makePayment(paymentRequest);

        if (paymentResponse == null) {
            throw new PaymentFailedException("Payment failed.");
        }

        return mapper.map(entity, OrderResponse.class);
    }

    @Override
    public List<OrderResponse> getAllOrders() {

        Type listType = new TypeToken<List<OrderResponse>>() {
        }.getType();

        return mapper.map(repository.findAll(), listType);
    }

    @Override
    public OrderResponse getOrder(Long id) {

        OrderEntity entity = repository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found with id : " + id));

        return mapper.map(entity, OrderResponse.class);
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderRequest request) {

        OrderEntity entity = repository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found with id : " + id));

        entity.setCustomerId(request.getCustomerId());
        entity.setProductId(request.getProductId());
        entity.setQuantity(request.getQuantity());
        entity.setTotalAmount(request.getTotalAmount());
        entity.setUpdatedDate(LocalDate.now());

        repository.save(entity);

        return mapper.map(entity, OrderResponse.class);
    }

    @Override
    public void deleteOrder(Long id) {

        if (!repository.existsById(id)) {
            throw new OrderNotFoundException(
                    "Order not found with id : " + id);
        }

        repository.deleteById(id);
    }

    public OrderResponse inventoryFallback(
            OrderRequest request,
            Exception ex){

        throw new RuntimeException(
                "Inventory Service is currently unavailable. Please try again later.");
    }
}