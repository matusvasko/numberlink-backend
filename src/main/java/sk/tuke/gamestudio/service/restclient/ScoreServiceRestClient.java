package sk.tuke.gamestudio.service.restclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ScoreServiceRestClient implements ScoreService {
    @Value("${remote.server.api}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addScore(Score score) {
        restTemplate.postForEntity(url + "/score", score, Score.class);
    }

    @Override
    public List<Score> getTopScores(String game) {
        return Arrays.asList(Objects.requireNonNull(
                restTemplate.getForEntity(url + "/score/" + game, Score[].class).getBody())
        );
    }

    @Override
    public void reset() {
        restTemplate.delete(url + "/score");
    }
}
