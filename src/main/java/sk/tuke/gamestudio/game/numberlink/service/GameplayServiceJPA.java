package sk.tuke.gamestudio.game.numberlink.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.game.numberlink.entity.Gameplay;
import sk.tuke.gamestudio.game.numberlink.repository.GameplayRepository;

import javax.transaction.Transactional;

@Transactional
@Service
public class GameplayServiceJPA implements GameplayService {
    @Autowired
    private GameplayRepository gameplayRepository;

    @Override
    public void save(Gameplay gameplay) {
        gameplayRepository.save(gameplay);
    }

    @Override
    public void delete(Gameplay gameplay) {
        gameplayRepository.deleteById(gameplay.getIdent());
    }

    @Override
    public Gameplay getGameplayById(Long ident) {
        return gameplayRepository.findGameplayByIdent(ident);
    }
}