package lunch_automate.com.example.app.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(@NotBlank String customerName, @NotBlank String paymentMethod, String observation,
                           @NotNull Boolean delivered, String address, List<LunchRequest> lunchRequestList) {
}
