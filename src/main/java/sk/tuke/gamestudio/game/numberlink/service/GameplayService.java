package sk.tuke.gamestudio.game.numberlink.service;

import sk.tuke.gamestudio.game.numberlink.entity.Gameplay;

public interface GameplayService {

    void save(Gameplay gameplay);

    void delete(Gameplay gameplay);

    Gameplay getGameplayById(Long id);
}
