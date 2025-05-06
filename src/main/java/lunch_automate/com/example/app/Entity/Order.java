package lunch_automate.com.example.app.Entity;

import jakarta.persistence.*;
import lunch_automate.com.example.app.Dto.OrderRequest;
import lunch_automate.com.example.app.Dto.OrderResponse;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tb_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String address;

    private String observation;

    private BigDecimal price;

    private Boolean delived;

    public OrderResponse toOrderResponse(OrderRequest orderRequest) {
        var convertPaymentEnum = Order.Payment.valueOf(orderRequest.paymentMethod().toUpperCase());
        var orderResponse = new OrderResponse(orderRequest.customerName(), orderRequest.paymentMethod(), orderRequest.observation(), orderRequest.delivered(), orderRequest.address(), orderRequest.lunchRequestList());
        return orderResponse;
    }

//    public Order toOrder(OrderRequest orderRequest) {
//        var convertPaymentEnum = Order.Payment.valueOf(orderRequest.paymentMethod().toUpperCase());
//        var order = new Order();
//        order.setCustomerName(orderRequest.customerName());
//        order.setAddress(orderRequest.address());
//        order.setObservation(orderRequest.observation());
//
//        var (order.getLunch(): lunch) {
//            var newLunch = new Lunch();
//
//        }
//
//    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lunch> lunch;

    private enum Payment {
        PIX(2L),
        CASH(1L),
        CREDIT_CARD(3L),
        PIX_CASH(4L),
        NO_PAYMENT(5L);

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
}
