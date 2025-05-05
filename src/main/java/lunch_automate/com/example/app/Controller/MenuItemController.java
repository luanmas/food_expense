package lunch_automate.com.example.app.Controller;

import jakarta.validation.Valid;
import lunch_automate.com.example.app.Dto.MenuItemRequest;
import lunch_automate.com.example.app.Dto.MenuItemResponse;
import lunch_automate.com.example.app.Service.MenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuItemController {
    private MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponse>> getAllOptions() {
        return ResponseEntity.ok(menuItemService.findAllMenuItem());
    }

    @PostMapping
    public ResponseEntity<MenuItemResponse> saveMenuItem(@RequestBody MenuItemRequest menuItemRequest) {
        return ResponseEntity.ok(menuItemService.saveMenuItem(menuItemRequest));
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<MenuItemResponse> deleteMenuItem(@PathVariable Long menuItemId) {
        return ResponseEntity.ok(menuItemService.deleteMenuItem(menuItemId));
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity<MenuItemResponse> updateMenuitem(@PathVariable Long menuItemId, @RequestBody @Valid MenuItemRequest menuItemRequest) {
        return ResponseEntity.ok(menuItemService.updateMenuItem(menuItemId, menuItemRequest));
    }
}

