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
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private long ident;

    @NotBlank
    private String game;

    @NotBlank
    private String player;

    @NotBlank
    private String text;

    @NotNull
    private Date commentedOn;

    public Comment(String game, String player, String text, Date commentedOn) {
        this.game = game;
        this.player = player;
        this.text = text;
        this.commentedOn = commentedOn;
    }
}