package sk.tuke.gamestudio.game.numberlink.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {
    private Field field;

    @Test
    void testIsPositionValid() {
        field = new Field(5, 6);
        assertTrue(field.isPositionValid(0, 0));
        assertTrue(field.isPositionValid(4, 4));
        assertFalse(field.isPositionValid(-1, 0));
        assertFalse(field.isPositionValid(0, -1));
        assertFalse(field.isPositionValid(5, 6));
    }

    @Test
    void testGetTile() {
        field = new Field(5, 6);
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 6; y++) {
                field.getTiles()[x][y] = new Number(1);
            }
        }
        assertNotNull(field.getTile(0, 0));
        assertNotNull(field.getTile(4, 4));
        assertNull(field.getTile(-1, 0));
        assertNull(field.getTile(0, -1));
        assertNull(field.getTile(5, 6));
    }

    @Test
    void testIsTileLinked() {
        field = new Field(5, 6);

        field.getTiles()[0][0] = new Number(1);
        assertFalse(field.isTileLinked(0, 0));
        field.linkTile(0, 0, Direction.UP);
        assertFalse(field.isTileLinked(0, 0));

        field.linkTile(0, 0, Direction.RIGHT);
        assertTrue(field.isTileLinked(0, 0));

        assertFalse(field.isTileLinked(0, 1));
        field.linkTile(0, 1, Direction.RIGHT);
        assertTrue(field.isTileLinked(0, 1));

        field.linkTile(0, 0, Direction.DOWN);
        assertFalse(field.isTileLinked(0, 0));
    }

    @Test
    void testCountSameNeighbours() {
        field = new Field(6, 5);
        field.getTiles()[0][0] = new Number(1);
        assertEquals(0, field.countSameNeighbours(0, 0));

        field.linkTile(0, 0, Direction.RIGHT);
        assertEquals(1, field.countSameNeighbours(0, 0));

        field.linkTile(0, 1, Direction.RIGHT);
        assertEquals(2, field.countSameNeighbours(0, 1));
    }


    @Test
    void testRemoveAllLinks() {
        field = new Field(9, 9);
        field.getTiles()[0][0] = new Link(1);
        field.getTiles()[0][1] = new Link(2);
        field.getTiles()[0][5] = new Link(3);

        // Remove all links
        field.removeAllLinks();

        // Check if all tiles are null after removing links
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                assertNull(field.getTiles()[row][col]);
            }
        }
    }

    @Test
    void testRemoveAllLinksWithValue() {
        field = new Field(6, 5);
        field.getTiles()[0][0] = new Link(1);
        field.getTiles()[0][1] = new Number(1);
        field.getTiles()[0][2] = new Link(1);

        field.removeAllLinksWithValue(1);
        assertNull(field.getTiles()[0][0]);
        assertNotNull(field.getTiles()[0][1]);
        assertNull(field.getTiles()[0][2]);
    }
}