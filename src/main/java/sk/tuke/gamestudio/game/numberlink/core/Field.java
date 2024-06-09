package sk.tuke.gamestudio.game.numberlink.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Field {
    private final int TIME_LIMIT = 120;
    private final int rowCount;
    private final int columnCount;
    private Tile[][] tiles;
    private GameState state = GameState.PLAYING;
    private GameTimer gameTimer;

    public Field(int rowCount, int columnCount) {
        if (rowCount <= 0 || columnCount <= 0) throw new IllegalArgumentException("Invalid field size");
        this.rowCount = rowCount;
        this.columnCount = columnCount;

        gameTimer = new GameTimer(this, TIME_LIMIT);
        tiles = new Tile[rowCount][columnCount];
    }

    public Tile getTile(int row, int column) {
        return isPositionValid(row, column) ? tiles[row][column] : null;
    }

    public boolean isPositionValid(int row, int column) {
        return row >= 0 && row < rowCount && column >= 0 && column < columnCount;
    }

    public void linkTile(int row, int column, Direction direction) {
        if (!isPositionValid(row, column)) throw new IllegalArgumentException("Invalid position");
        if (direction == null) throw new IllegalArgumentException("Invalid direction");

        var position = tiles[row][column];
        if (position == null) return;
        if (isTileLinked(row, column)) {
            removeAllLinksWithValue(position.getValue());
            return;
        }

        int destinationRow = row + direction.getY();
        int destinationColumn = column + direction.getX();
        if (!isPositionValid(destinationRow, destinationColumn)) return;

        if (tiles[destinationRow][destinationColumn] == null)
            tiles[destinationRow][destinationColumn] = new Link(position.getValue());
        if (isSolved()) {
            state = GameState.SOLVED;
            System.out.println(state);
        }
    }

    public boolean isSolved() {
        int linkedTilesCount = 0;
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                if (isTileLinked(row, column)) linkedTilesCount++;
            }
        }
        return linkedTilesCount == rowCount * columnCount;
    }

    public boolean isTileLinked(int row, int column) {
        if (!isPositionValid(row, column)) throw new IllegalArgumentException("Invalid position");

        var tile = tiles[row][column];
        if (tile == null) return false;
        int numberOfLinks = countSameNeighbours(row, column);

        if (tile instanceof Number && numberOfLinks == 1) {     // (1)-Number can only be linked on one side
            return true;
        } else {
            return (tile instanceof Link && numberOfLinks == 2);
        }
    }

    public int countSameNeighbours(int row, int column) {
        if (!isPositionValid(row, column)) throw new IllegalArgumentException("Invalid position");
        if (tiles[row][column] == null) return 0;

        var rootTileValue = tiles[row][column].getValue();
        int count = 0;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // up, down, left, right

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newColumn = column + dir[1];

            if (isPositionValid(newRow, newColumn) && tiles[newRow][newColumn] != null
                    && tiles[newRow][newColumn].getValue() == rootTileValue) {
                count++;
            }
        }
        return count;
    }

    void removeAllLinks() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                if (tiles[row][column] instanceof Link)
                    tiles[row][column] = null;
            }
        }
    }

    void removeAllLinksWithValue(int value) {
        if (value < 0) return;
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                var tile = tiles[row][column];
                if (tile != null)
                    if (tile.getValue() == value && tile instanceof Link)
                        tiles[row][column] = null;
            }
        }
    }

    public int[][] getLinks() {
        int[][] links = new int[rowCount][columnCount];

        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                var tile = tiles[row][column];
                if (tile instanceof Link) {
                    links[row][column] = tile.getValue();
                }
            }
        }
        return links;
    }

    public void setLinks(int[][] links) {
        if (links == null || links.length != rowCount || links[0].length != columnCount) return;

        removeAllLinks();

        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                var link = links[row][column];
                if (link > 0)
                    tiles[row][column] = new Link(link);
            }
        }
    }
}