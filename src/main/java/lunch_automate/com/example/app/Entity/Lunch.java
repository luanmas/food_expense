package lunch_automate.com.example.app.Entity;

import jakarta.persistence.*;
import lunch_automate.com.example.app.Dto.MenuItemRequest;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_lunch")
public class Lunch {
    @Id
    @Column(name = "lunch_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_lunch_menu_item",
            joinColumns = @JoinColumn(name = "lunch_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_item_id")
    )
    private List<MenuItem> menuItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private Types type;

    public enum Types {
        SMALL(2L),
        NORMAL(1L);

        private Long id;

        Types(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }


    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void setMenuItemsRequest(List<MenuItemRequest> menuItemsRequest) {
        List<MenuItem> menuItems = menuItemsRequest.stream()
                .map(menuItemRequest -> {
                    MenuItem menuItem = new MenuItem();
                    menuItem.setType(MenuItem.Type.valueOf(menuItemRequest.type()));
                    menuItem.setMenu(menuItemRequest.menuItem());
                    return menuItem;
                }).collect(Collectors.toList());
        setMenuItems(menuItems);
    }

    public String getType() {
        return this.type.name();
    }

    public void setType(String type) {
        this.type = this.type.valueOf(type);
    }

    public void setType(Types type) {
        this.type = type;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
