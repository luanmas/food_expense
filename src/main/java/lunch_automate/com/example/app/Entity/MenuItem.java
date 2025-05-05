package lunch_automate.com.example.app.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_menu_item")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_item__id")
    private Long id;

    private String menuItem;

    @Enumerated(EnumType.STRING)
    private Type type;

    public MenuItem(String menuItem, Type type) {
        this.menuItem = menuItem;
        this.type = type;
    }

    public MenuItem() {
    }

    public enum Type {
        SIMPLE(2L),
        PREMIUM(1L);

        private Long id;

        Type(Long id) {
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

    public String getMenuItem() {
        return menuItem;
    }

    public void setMenu(String menuItem) {
        this.menuItem = menuItem;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
