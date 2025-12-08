package use.gutierrez.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import use.gutierrez.dto.LoginRequest;
import use.gutierrez.security.config.utils.JwtUtil;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

    if (!loginRequest.username().equals("admin") || !loginRequest.password().equals("admin")) {
      return ResponseEntity.status(401).body("Invalid credentials");
    }

    String role = "ADMIN";
    String token = JwtUtil.generateToken(loginRequest.username(), role);

    return ResponseEntity.ok().body(
        Map.of(
            "username", loginRequest.username(),
            "role", role,
            "token", token
        )
    );
  }
}
