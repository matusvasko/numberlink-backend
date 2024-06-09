package sk.tuke.gamestudio.game.numberlink.service;

import sk.tuke.gamestudio.game.numberlink.entity.Gameplay;
import sk.tuke.gamestudio.game.numberlink.entity.Move;

public interface MoveService {

    void save(Move move);

    Move getLatest(Gameplay gameplay);

    void delete(Gameplay gameplay);

    int count(Gameplay gameplay);
}
