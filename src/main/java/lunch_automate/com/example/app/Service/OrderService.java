package lunch_automate.com.example.app.Service;

import lunch_automate.com.example.app.Dto.LunchResponse;
import lunch_automate.com.example.app.Dto.OrderRequest;
import lunch_automate.com.example.app.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

//    public List<LunchResponse> createOrder(OrderRequest orderRequest) {
//
//    }
}
