package lunch_automate.com.example.app.Controller;

import jakarta.validation.Valid;
import lunch_automate.com.example.app.Dto.OrderRequest;
import lunch_automate.com.example.app.Dto.OrderResponse;
import lunch_automate.com.example.app.Entity.Order;
import lunch_automate.com.example.app.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        var orderResponse = orderService.createOrder(orderRequest);

        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping
    public  ResponseEntity<List<Order>> getAllOrders() {
        var orderList = orderService.findAllOrders();

        return ResponseEntity.ok(orderList);
    }
}
