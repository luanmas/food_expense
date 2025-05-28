package lunch_automate.com.example.app.Dto;

import java.util.List;

public record OrderResponse (Long id, String customerName, String payment, String observation, Boolean delivered, String address, List<LunchResponse> listLunchResponse) {
}
