package lunch_automate.com.example.app.Service;

import lunch_automate.com.example.app.Dto.LunchRequest;
import lunch_automate.com.example.app.Dto.LunchResponse;
import lunch_automate.com.example.app.Entity.Lunch;
import lunch_automate.com.example.app.Entity.MenuItem;
import lunch_automate.com.example.app.Repository.LunchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LunchService {
    private LunchRepository lunchRepository;

    public LunchService(LunchRepository lunchRepository) {
        this.lunchRepository = lunchRepository;
    }

    public LunchResponse createLunch(LunchRequest lunchRequest) {
        var lunch = new Lunch();
        lunch.setType(lunchRequest.type());
        List<MenuItem> menuItems = lunchRequest.menuItemList().stream()
                .map(menuItem -> {
                    var item = new MenuItem();
                    item.setMenu(menuItem.menuItem());
                    item.setType(MenuItem.Type.valueOf(menuItem.type()));
                    return item;
                })
                .toList();

        lunch.setMenuItems(menuItems);
        var lunchCreated = lunchRepository.save(lunch);

        return new LunchResponse(lunchCreated.getId(), lunchCreated.getMenuItems(), lunchCreated.getType());
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
