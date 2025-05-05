package lunch_automate.com.example.app.Dto;

import jakarta.validation.constraints.NotBlank;

public record MenuItemRequest(@NotBlank String menuItem, @NotBlank String type) {
}
