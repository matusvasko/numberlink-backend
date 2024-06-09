package sk.tuke.gamestudio.game.numberlink.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class FieldGenerator {
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 10;

    public Field generateField(int difficulty) {
        if (difficulty < 1 || difficulty > 3) throw new IllegalArgumentException("Difficulty must be between 1 and 3");

        String fileName = "src/main/resources/maps";
        Random random = new Random();
        int fieldNumber = random.nextInt(MAX_VALUE) + MIN_VALUE;

        switch (difficulty) {
            case 1:
                fileName += "/easy/easy" + fieldNumber + ".txt";
                break;
            case 2:
                fileName += "/medium/medium" + fieldNumber + ".txt";
                break;
            case 3:
                fileName += "/hard/hard" + fieldNumber + ".txt";
                break;
        }
        return readFieldFromFile(fileName);
    }

    private Field readFieldFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String[] dimensions = reader.readLine().split("\\s+");
            int columns = Integer.parseInt(dimensions[0]);
            int rows = Integer.parseInt(dimensions[1]);

            Field field = new Field(rows, columns);
            Tile[][] tiles = field.getTiles();

            for (int i = 0; i < rows; i++) {
                String[] values = reader.readLine().split("\\s+");
                for (int j = 0; j < columns; j++) {
                    int value = Integer.parseInt(values[j]);
                    if (value > 0) tiles[i][j] = new Number(value);
                }
            }

            return field;
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            return null;
        }
    }
}