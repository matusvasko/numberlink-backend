package sk.tuke.gamestudio.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(Rating.RatingId.class)
public class Rating {
    @Id
    @GeneratedValue
    private long ident;

    @Id
    @NotBlank
    private String game;

    @Id
    @NotBlank
    private String player;

    @Min(1)
    @Max(5)
    private int rating;

    public Rating(String game, String player, int rating) {
        this.game = game;
        this.player = player;
        this.rating = rating;
    }

    @Data
    public static class RatingId implements Serializable {
        private long ident;
        private String game;
        private String player;
    }
}