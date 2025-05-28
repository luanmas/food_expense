package lunch_automate.com.example.app.Controller;

import jakarta.validation.Valid;
import lunch_automate.com.example.app.Dto.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;

    public  AuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestParam @Valid AuthenticationRequest auth) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(auth.email(), auth.password());
        var token =  authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();
    }
}
