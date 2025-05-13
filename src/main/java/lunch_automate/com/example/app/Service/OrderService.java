package lunch_automate.com.example.app.Service;

import lunch_automate.com.example.app.Dto.OrderRequest;
import lunch_automate.com.example.app.Dto.OrderResponse;
import lunch_automate.com.example.app.Entity.Order;
import lunch_automate.com.example.app.Repository.MenuItemRepository;
import lunch_automate.com.example.app.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private LunchService lunchService;
    private OrderRepository orderRepository;

    public OrderService(LunchService lunchService, OrderRepository orderRepository) {
        this.lunchService = lunchService;
        this.orderRepository = orderRepository;
    }


    public OrderResponse createOrder(OrderRequest orderRequest) {
        var payment = Order.Payment.valueOf(orderRequest.paymentMethod().toUpperCase());

        var orderCreate = new Order();
        orderCreate.setPayment(payment);
        orderCreate.setCustomerName(orderRequest.customerName());
        orderCreate.setDelived(orderRequest.delivered());
        orderCreate.setAddress(orderRequest.address());
        orderCreate.setObservation(orderRequest.observation());

        var savedOrder = orderRepository.save(orderCreate);

        var lunchList = orderRequest.lunchRequestList().stream()
                .map(lunchRequest -> lunchService.createLunch(lunchRequest, savedOrder))
                .collect(Collectors.toCollection(ArrayList::new));

        savedOrder.setLunch(lunchList);

        orderRepository.save(savedOrder);

        return savedOrder.toOrderResponse(orderRequest);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
