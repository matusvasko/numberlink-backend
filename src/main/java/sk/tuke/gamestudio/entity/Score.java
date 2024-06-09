package sk.tuke.gamestudio.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Score {
    @Id
    @GeneratedValue
    private long ident;

    @NotBlank
    private String game;

    @NotBlank
    private String player;

    @Positive
    private int points;

    @NotNull
    private Date playedOn;

    public Score(String game, String player, int points, Date playedOn) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.playedOn = playedOn;
    }
}
