package lunch_automate.com.example.app.Entity;

import jakarta.persistence.*;
import lunch_automate.com.example.app.Dto.LunchRequest;
import lunch_automate.com.example.app.Dto.LunchResponse;
import lunch_automate.com.example.app.Dto.OrderRequest;
import lunch_automate.com.example.app.Dto.OrderResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private String customerName;

    private String address;

    private String observation;

    private BigDecimal price;

    private Boolean delived;

    @Enumerated(EnumType.STRING)
    private Payment payment;

//    public OrderResponse toOrderResponse(OrderRequest orderRequest) {
//        var orderResponse = new OrderResponse(orderRequest.customerName(), orderRequest.paymentMethod(), orderRequest.observation(), orderRequest.delivered(), orderRequest.address(), orderRequest.lunchRequestList());
//        return orderResponse;
//    }

    public OrderResponse toOrderResponse(Order order) {
        List<LunchResponse> listLunchResponse = order.getLunch().stream()
                .map(lunch -> {
                    LunchResponse lunchResponse = new LunchResponse(lunch.getId(), lunch.getMenuItems(), lunch.getType());
                    return lunchResponse;
                }).collect(Collectors.toCollection(ArrayList::new));
        OrderResponse orderResponse = new OrderResponse(order.getId(), order.getCustomerName(), order.getPayment().name(), order.getObservation(), order.getDelived(), order.getAddress(), listLunchResponse);
        return orderResponse;
    }

    public Order toOrder(OrderRequest orderRequest) {
        var convertPaymentEnum = Order.Payment.valueOf(orderRequest.paymentMethod().toUpperCase());
        var order = new Order();
        order.setCustomerName(orderRequest.customerName());
        order.setAddress(orderRequest.address());
        order.setObservation(orderRequest.observation());
        order.setPayment(convertPaymentEnum);

        List<Lunch> lunches = orderRequest.lunchRequestList().stream().map(lunchRequest -> {
            Lunch newLunch = new Lunch();
            newLunch.setType(lunchRequest.type());
            newLunch.setOrder(order);
            return newLunch;
        }).collect(Collectors.toList());

        order.setLunch(lunches);
        return order;

    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lunch> lunch;

    public enum Payment {
        PIX(2L), CASH(1L), CREDIT_CARD(3L), PIX_CASH(4L), NO_PAYMENT(5L);

        private Long id;

        Payment(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }

    private BigDecimal total;

    private BigDecimal totalWithDiscount;

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Lunch> getLunch() {
        return lunch;
    }

    public void setLunch(List<Lunch> lunch) {
        this.lunch = lunch;
    }

    public void setLunchFromRequest(List<LunchRequest> lunchRequestList) {
        List<Lunch> lunches = lunchRequestList.stream().map(request -> {
            Lunch lunch = new Lunch();
            lunch.setType(Lunch.Types.valueOf(request.type()));
            lunch.setMenuItems(request.menuItemList());
            return lunch;
        }).toList();
        this.lunch = lunches;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalWithDiscount() {
        return totalWithDiscount;
    }

    public void setTotalWithDiscount(BigDecimal totalWithDiscount) {
        this.totalWithDiscount = totalWithDiscount;
    }

    public Boolean getDelived() {
        return delived;
    }

    public void setDelived(Boolean delived) {
        this.delived = delived;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
