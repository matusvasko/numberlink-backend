package sk.tuke.gamestudio.game.numberlink.core;

import lombok.Getter;

@Getter
public abstract class Tile {
    protected int value;
    protected TileType type;

    public Tile(int value, TileType type) {
        this.value = value;
        this.type = type;
    }
}
