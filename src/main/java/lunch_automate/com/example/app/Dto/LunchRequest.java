package lunch_automate.com.example.app.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record LunchRequest(@NotNull @NotEmpty List<@Valid MenuItemRequest> menuItemList, @NotBlank String type) {
}
