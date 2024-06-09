package sk.tuke.gamestudio.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.repository.RatingRepository;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingService;

import javax.transaction.Transactional;

@Transactional
@Service
public class RatingServiceJPA implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public void setRating(Rating rating) throws RatingException {
        ratingRepository.save(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        Integer averageRating = ratingRepository.getAverageRatingByGame(game);
        return averageRating == null ? 0 : averageRating;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return ratingRepository.findRatingByGameAndPlayer(game, player).getRating();
    }

    @Override
    public void reset() throws RatingException {
        ratingRepository.deleteAll();
    }
}
