package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/score")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    // GET http://localhost:8080/api/score/numberlink
    @GetMapping("/{game}")
    public List<Score> getTopScores(@PathVariable String game) {
        return scoreService.getTopScores(game);
    }

    // POST http://localhost:8080/api/score
    @PostMapping
    public void addScore(@RequestBody Score score) {
        scoreService.addScore(score);
    }

    // DELETE http://localhost:8080/api/score
    @DeleteMapping
    public void reset() {
        scoreService.reset();
    }
}
