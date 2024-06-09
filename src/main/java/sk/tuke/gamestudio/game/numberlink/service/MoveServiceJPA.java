package sk.tuke.gamestudio.game.numberlink.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.game.numberlink.entity.Gameplay;
import sk.tuke.gamestudio.game.numberlink.entity.Move;
import sk.tuke.gamestudio.game.numberlink.repository.MoveRepository;

@Service
public class MoveServiceJPA implements MoveService {
    @Autowired
    private MoveRepository moveRepository;

    @Override
    public void save(Move move) {
        moveRepository.save(move);
    }

    @Override
    public Move getLatest(Gameplay gameplay) {
        return moveRepository.findFirstByGameplayOrderByTimeDesc(gameplay);
    }

    @Override
    public void delete(Gameplay gameplay) {
        Move lastMove = moveRepository.findFirstByGameplayOrderByTimeDesc(gameplay);
        moveRepository.deleteById(lastMove.getIdent());
    }

    @Override
    public int count(Gameplay gameplay) {
        return moveRepository.countByGameplay(gameplay);
    }
}
