package sk.tuke.gamestudio.game.numberlink.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.tuke.gamestudio.game.numberlink.entity.Gameplay;

@Repository
public interface GameplayRepository extends JpaRepository<Gameplay, Long> {
    Gameplay findGameplayByIdent(Long ident);
}