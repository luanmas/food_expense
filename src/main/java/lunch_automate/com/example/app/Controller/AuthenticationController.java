package lunch_automate.com.example.app.Controller;

import jakarta.validation.Valid;
import lunch_automate.com.example.app.Dto.AuthenticationRequest;
import lunch_automate.com.example.app.Dto.LoginResponse;
import lunch_automate.com.example.app.Dto.RegisterRequest;
import lunch_automate.com.example.app.Entity.User;
import lunch_automate.com.example.app.Repository.UserRepository;
import lunch_automate.com.example.app.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private TokenService tokenService;

    public  AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthenticationRequest data) {
        System.out.println(data.email());
        System.out.println(data.password());
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth =  authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequest register) {
        if (this.userRepository.findByEmail(register.email()) != null){
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(register.password());
        User newUser = new User(register.email(), encryptedPassword, register.role());
        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
