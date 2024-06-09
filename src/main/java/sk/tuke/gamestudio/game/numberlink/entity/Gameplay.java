package sk.tuke.gamestudio.game.numberlink.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Gameplay {
    @Id
    @GeneratedValue
    private Long ident;

    @NotBlank
    private String player;

    @NotBlank
    private Date timeStarted;

    public Gameplay(String player, Date timeStarted) {
        this.player = player;
        this.timeStarted = timeStarted;
    }
}