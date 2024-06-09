package sk.tuke.gamestudio.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sk.tuke.gamestudio.dto.AuthRequestDTO;
import sk.tuke.gamestudio.dto.AuthResponseDTO;
import sk.tuke.gamestudio.dto.RegisterRequestDTO;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.entity.Role;
import sk.tuke.gamestudio.repository.PlayerRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public boolean isInDatabase(AuthRequestDTO request) {
        return playerRepository.findByUsername(request.getUsername()).isPresent();
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        Player player = new Player(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                Role.PLAYER);

        playerRepository.save(player);

        var jwtToken = jwtService.generateToken(player);

        return AuthResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        var playerOptional = playerRepository.findByUsername(request.getUsername());

        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();

            String storedPassword = player.getPassword();

            if (passwordEncoder.matches(request.getPassword(), storedPassword)) {
                var jwtToken = jwtService.generateToken(player);
                return AuthResponseDTO.builder().token(jwtToken).build();
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username or password");
    }

    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        var player = playerRepository.findByUsername(request.getUsername()).orElseThrow();

        var jwtToken = jwtService.generateToken(player);

        return AuthResponseDTO.builder()
                .token(jwtToken)
                .build();
    }
}
