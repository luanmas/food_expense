package lunch_automate.com.example.app.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lunch_automate.com.example.app.Entity.Order;

import java.util.List;

public record OrderResponse (String customerName, String payment, String observation, Boolean delivered, String address, List<LunchRequest> lunchRequestList) {
}
