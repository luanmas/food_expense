package lunch_automate.com.example.app.Service;

import lunch_automate.com.example.app.Dto.LunchRequest;
import lunch_automate.com.example.app.Dto.LunchResponse;
import lunch_automate.com.example.app.Entity.Lunch;
import lunch_automate.com.example.app.Entity.MenuItem;
import lunch_automate.com.example.app.Entity.Order;
import lunch_automate.com.example.app.Repository.LunchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            var listMenuItems = menuItemService.findAllMenu(lunch.getMenuItems());
            lunchResponse.add(new LunchResponse(lunch.getId(), listMenuItems, lunch.getType()));
        }

        return lunchResponse;
    }

    public List<Lunch> verifyMenuItemOnLunch(List<LunchRequest> lunchResponse, Order order) {
        var lunchesUpdated = lunchResponse.stream().map(lunch -> {
            var newLunch = new Lunch();
            List<MenuItem> menuItems =  menuItemService.findAllMenu(lunch.menuItemList());
            newLunch.setMenuItems(menuItems);
            newLunch.setType(lunch.type());
            newLunch.setOrder(order);
            return lunchRepository.save(newLunch);
        }).collect(Collectors.toCollection(ArrayList::new));

        return lunchesUpdated;
    }

//    public List<LunchResponse> findAllLunchRequests(List<LunchRequest> lunchRequests) {
//        var menuItems = lunchRequests.stream()
//                .map(lunchRequest -> {
//                    return menuItemService.findAllMenu(lunchRequest.menuItemList());
//                }).collect(Collectors.toCollection(ArrayList::new));
//
//        var lunchResponse = new ArrayList<LunchResponse>();
//    }
}
