package com.example.OrderService;

import com.example.OrderService.exception.PaymentFailedException;
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
    public OrderResponse createOrder(OrderRequest request) {

        ProductResponse product =
                productClient.getProduct(request.getProductId());

        if (product == null) {
            throw new RuntimeException("Product Not Found");
        }

        InventoryResponse inventory =
                inventoryClient.getInventory(request.getProductId());

        if (inventory.getAvailableStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock available!");
        }

        PaymentRequest paymentRequest = new PaymentRequest();

        paymentRequest.setAmount(product.getPrice() * request.getQuantity());
        paymentRequest.setPaymentMethod("UPI");

        PaymentResponse paymentResponse = paymentClient.makePayment(paymentRequest);
        if (paymentResponse == null) {
            throw new PaymentFailedException("Payment Failed");
        }
        OrderEntity entity = new OrderEntity();

        entity.setCustomerId(request.getCustomerId());
        entity.setProductId(request.getProductId());
        entity.setQuantity(request.getQuantity());

        entity.setTotalAmount(product.getPrice() * request.getQuantity());
        entity.setStatus("CREATED");
        entity.setCreatedDate(LocalDate.now());
        entity.setUpdatedDate(LocalDate.now());

        inventoryClient.reduceStock(request.getProductId(), request.getQuantity());

        repository.save(entity);
        return mapper.map(entity, OrderResponse.class);
    }

    @Override
    public List<OrderResponse> getAllOrders() {

        Type listType = new TypeToken<List<OrderResponse>>(){}.getType();

        return mapper.map(repository.findAll(),listType);
    }

    @Override
    public OrderResponse getOrder(Long id) {

        OrderEntity entity = repository.findById(id).orElse(null);

        if(entity==null){
            return null;
        }

        return mapper.map(entity,OrderResponse.class);
    }

    @Override
    public void updateOrder(Long id, OrderRequest request) {

        OrderEntity entity = repository.findById(id).orElse(null);

        if(entity==null){
            return;
        }

        entity.setCustomerId(request.getCustomerId());
        entity.setProductId(request.getProductId());
        entity.setQuantity(request.getQuantity());
        entity.setTotalAmount(request.getTotalAmount());
        entity.setUpdatedDate(LocalDate.now());

        repository.save(entity);
    }

    @Override
    public void deleteOrder(Long id) {

        repository.deleteById(id);
    }
}
