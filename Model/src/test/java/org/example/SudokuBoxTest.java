package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SudokuBoxTest {

    @Test
    void testToString() {
        // Arrange
        List<SudokuField> fields = Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3));
        SudokuBox box = new SudokuBox(fields);

        String actual = box.toString();
        System.out.println("Actual toString(): " + actual);
        assertEquals("SudokuBox[Box=[SudokuField[value=1], SudokuField[value=2], SudokuField[value=3]]]", actual);

    }
}
