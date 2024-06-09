package sk.tuke.gamestudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sk.tuke.gamestudio.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT CAST(AVG(r.rating) + 0.5 AS int) FROM Rating r WHERE r.game = ?1")
    Integer getAverageRatingByGame(String game);

    Rating findRatingByGameAndPlayer(String game, String player);
}
