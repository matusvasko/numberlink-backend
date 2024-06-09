package sk.tuke.gamestudio.game.numberlink.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FieldGeneratorTest {

    @Test
    void testGenerateField() {
        FieldGenerator fieldGenerator = new FieldGenerator();
        Field fieldEasy = fieldGenerator.generateField(1);
        assertNotNull(fieldEasy);
        Field fieldMedium = fieldGenerator.generateField(2);
        assertNotNull(fieldMedium);
        Field fieldHard = fieldGenerator.generateField(3);
        assertNotNull(fieldHard);
    }
}
