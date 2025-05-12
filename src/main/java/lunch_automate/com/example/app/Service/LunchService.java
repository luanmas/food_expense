package lunch_automate.com.example.app.Service;

import lunch_automate.com.example.app.Dto.LunchRequest;
import lunch_automate.com.example.app.Dto.LunchResponse;
import lunch_automate.com.example.app.Entity.Lunch;
import lunch_automate.com.example.app.Entity.MenuItem;
import lunch_automate.com.example.app.Entity.Order;
import lunch_automate.com.example.app.Repository.LunchRepository;
import lunch_automate.com.example.app.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LunchService {
    private LunchRepository lunchRepository;
    private MenuItemService menuItemService;

    public LunchService(LunchRepository lunchRepository, MenuItemService menuItemService) {
        this.lunchRepository = lunchRepository;
        this.menuItemService = menuItemService;
    }

    public Lunch createLunch(LunchRequest lunchRequest, Order order) {
        var lunch = new Lunch();
        lunch.setType(lunchRequest.type());
        lunch.setOrder(order);
        var menuItems = menuItemService.findAllMenu(lunchRequest.menuItemList());

        lunch.setMenuItems(menuItems);
        return lunchRepository.save(lunch);
    }

    public List<LunchResponse> findAllLunches() {
        var lunches = lunchRepository.findAll();
        var lunchResponse = new ArrayList<LunchResponse>();

        for  (Lunch lunch : lunches) {
            lunchResponse.add(new LunchResponse(lunch.getId(), lunch.getMenuItems(), lunch.getType()));
        }

        return lunchResponse;
    }
}
