package lunch_automate.com.example.app.Service;

import lunch_automate.com.example.app.Dto.OrderRequest;
import lunch_automate.com.example.app.Dto.OrderResponse;
import lunch_automate.com.example.app.Entity.Order;
import lunch_automate.com.example.app.Exceptions.NotFoundOrderException;
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
        return orderCreate.toOrderResponse(savedOrder);
    }

    public List<OrderResponse> findAllOrders() {
        List<OrderResponse> listOrderResponse = orderRepository.findAll().stream()
                .map(order -> {
                    Order orderResponse = new Order();
                    return orderResponse.toOrderResponse(order);
                }).collect(Collectors.toCollection(ArrayList::new));

        return listOrderResponse;
    }

    public Order deleteOrder(Long orderId) {
        var orderToDelete = orderRepository.findById(orderId).orElseThrow(NotFoundOrderException::new);
        orderRepository.delete(orderToDelete);

        return orderToDelete;
    }

    public OrderResponse updateOrder(Long orderId, OrderRequest orderRequest) {
        Order order = orderRepository.findById(orderId).orElseThrow(NotFoundOrderException::new);
        order.setCustomerName(orderRequest.customerName());
        order.setObservation(orderRequest.observation());
        order.setAddress(orderRequest.address());
        order.setPayment(Order.Payment.valueOf(orderRequest.paymentMethod().toUpperCase()));
        order.setDelived(orderRequest.delivered());

        var lunchList = lunchService.verifyMenuItemOnLunch(orderRequest.lunchRequestList(), order);
        order.setLunch(lunchList);


        var orderUpdated = orderRepository.save(order);
        return order.toOrderResponse(orderUpdated);
    }
}
