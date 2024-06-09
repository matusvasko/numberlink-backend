package sk.tuke.gamestudio.game.numberlink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.tuke.gamestudio.game.numberlink.entity.Gameplay;
import sk.tuke.gamestudio.game.numberlink.entity.Move;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {
    Move findFirstByGameplayOrderByTimeDesc(Gameplay gameplay);

    Integer countByGameplay(Gameplay gameplay);
}
