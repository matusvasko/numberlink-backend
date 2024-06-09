package sk.tuke.gamestudio.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.dto.AuthRequestDTO;
import sk.tuke.gamestudio.dto.AuthResponseDTO;
import sk.tuke.gamestudio.dto.RegisterRequestDTO;
import sk.tuke.gamestudio.service.auth.AuthService;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // POST http://localhost:8080/api/auth/check
    @PostMapping("/check")
    public ResponseEntity<Boolean> check(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.isInDatabase(request));
    }

    // POST http://localhost:8080/api/auth/register
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    // POST http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // POST http://localhost:8080/api/auth/authenticate
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
