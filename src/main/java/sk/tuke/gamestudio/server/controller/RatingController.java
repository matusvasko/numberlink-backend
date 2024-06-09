package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;

@CrossOrigin
@RestController
@RequestMapping("/api/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    // GET http://localhost:8080/api/rating/numberlink/average
    @GetMapping("/{game}/average")
    public int getAverageRating(@PathVariable String game) {
        return ratingService.getAverageRating(game);
    }

    // GET http://localhost:8080/api/rating/numberlink/{player_name}
    @GetMapping("/{game}/{player}")
    public int getRating(@PathVariable String game, @PathVariable String player) {
        return ratingService.getRating(game, player);
    }

    // POST http://localhost:8080/api/rating/
    @PostMapping
    public void addRating(@RequestBody Rating rating) {
        ratingService.setRating(rating);
    }

    // DELETE http://localhost:8080/api/rating/
    @DeleteMapping
    public void reset() {
        ratingService.reset();
    }
}
