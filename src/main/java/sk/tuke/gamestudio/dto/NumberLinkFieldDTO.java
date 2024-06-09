package sk.tuke.gamestudio.dto;

import lombok.Data;
import sk.tuke.gamestudio.game.numberlink.core.GameState;
import sk.tuke.gamestudio.game.numberlink.core.Tile;

@Data
public class NumberLinkFieldDTO {
    private int rowCount;
    private int columnCount;
    private GameState state;
    private Tile[][] tiles;
}
