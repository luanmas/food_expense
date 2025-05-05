package lunch_automate.com.example.app.Dto;

import lunch_automate.com.example.app.Entity.MenuItem;

import java.util.List;

public record LunchResponse(Long id, List<MenuItem> menuItemList, String type) {
}
