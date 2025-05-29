package lunch_automate.com.example.app.Dto;

import lunch_automate.com.example.app.Entity.User;

public record RegisterRequest(String email, String password, User.Role role) {
}
