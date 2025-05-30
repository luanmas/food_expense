package lunch_automate.com.example.app.Service;

import lunch_automate.com.example.app.Dto.MenuItemRequest;
import lunch_automate.com.example.app.Dto.MenuItemResponse;
import lunch_automate.com.example.app.Entity.MenuItem;
import lunch_automate.com.example.app.Exceptions.MenuNotFoundExistException;
import lunch_automate.com.example.app.Repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemService {
    private MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItemResponse saveMenuItem(MenuItemRequest menuItemRequest) {
        var type = MenuItem.Type.valueOf(menuItemRequest.type().toUpperCase());
        var newMenuItem = new MenuItem(menuItemRequest.menuItem(), type);

        var menuItemCreated = menuItemRepository.save(newMenuItem);
        return new MenuItemResponse(menuItemCreated.getId(), menuItemCreated.getMenuItem(), menuItemCreated.getType().name());
    }

    public List<MenuItem> findAllMenuItem() {
        var menuItems = menuItemRepository.findAll();

        return menuItems;
    }

    public List<MenuItem> findAllMenu(List<MenuItem> menuItems) {
        var menuItemAlreadyExists = menuItems.stream()
                .map(menuItem -> {
                    var itemExist = menuItemRepository.findById(menuItem.getId()).orElseThrow(MenuNotFoundExistException::new);
                    return itemExist;
                }).collect(Collectors.toCollection(ArrayList::new));

        return menuItemAlreadyExists;
    }

    public MenuItemResponse updateMenuItem(Long id, MenuItemRequest menuItemRequest) {
        var menuItem = menuItemRepository.findById(id).orElseThrow(MenuNotFoundExistException::new);
        var converterType = MenuItem.Type.valueOf(menuItemRequest.type().toUpperCase());
        menuItem.setMenu(menuItemRequest.menuItem());
        menuItem.setType(converterType);
        var menuItemSaved = menuItemRepository.save(menuItem);

        return new MenuItemResponse(menuItemSaved.getId(), menuItemSaved.getMenuItem(), menuItemSaved.getType().name());
    }

    public MenuItemResponse deleteMenuItem(Long id) {
        var menuItem = menuItemRepository.findById(id).orElseThrow(MenuNotFoundExistException::new);
        menuItemRepository.delete(menuItem);

        return  new MenuItemResponse(menuItem.getId(), menuItem.getMenuItem(), menuItem.getType().name());
    }

    public List<MenuItem> findById (List<MenuItem> menuItems) {
        var listMenuItems = menuItems.stream()
                .map(menuItem -> menuItemRepository.findById(menuItem.getId()).orElseThrow(MenuNotFoundExistException::new))
                .collect(Collectors.toCollection(ArrayList::new));

        return listMenuItems;
    }
}
