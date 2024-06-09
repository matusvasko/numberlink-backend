package sk.tuke.gamestudio.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.repository.ScoreRepository;
import sk.tuke.gamestudio.service.ScoreException;
import sk.tuke.gamestudio.service.ScoreService;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class ScoreServiceJPA implements ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public void addScore(Score score) throws ScoreException {
        scoreRepository.save(score);
    }

    @Override
    public List<Score> getTopScores(String game) throws ScoreException {
        return scoreRepository.findTop10ByGameOrderByPointsDesc(game);
    }

    @Override
    public void reset() {
        scoreRepository.deleteAll();
    }
}
