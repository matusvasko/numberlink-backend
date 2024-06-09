package sk.tuke.gamestudio.game.numberlink.entity;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Move {
    @Id
    @GeneratedValue
    private Long ident;

    @ManyToOne
    @JoinColumn(name = "ident_gameplay")
    private Gameplay gameplay;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String links;

    @NotBlank
    private Date time;

    public Move(Gameplay gameplay, String links, Date time) {
        this.gameplay = gameplay;
        this.links = links;
        this.time = time;
    }

    public int[][] getLinks() {
        return new Gson().fromJson(links, int[][].class);
    }
}
