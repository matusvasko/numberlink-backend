package sk.tuke.gamestudio.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.repository.PlayerRepository;

import javax.transaction.Transactional;

@Transactional
@Service
public class PlayerServiceJPA implements UserDetailsService {
    @Autowired
    private PlayerRepository playerRepository;

    public Player findPlayer(String username, String password) {
        return playerRepository
                .findByUsernameAndPassword(username, password)
                .orElse(null);
    }

    public void savePlayer(Player player) {
        playerRepository.save(player);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return playerRepository
                .findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
