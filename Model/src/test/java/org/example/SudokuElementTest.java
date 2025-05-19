package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuElementTest {
    @Test
    void testEqualsSameObject() {
        // Arrange
        SudokuRow row = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));

        // Act & Assert
        assertTrue(row.equals(row), "An object should be equal to itself");
    }

    @Test
    void testEqualsNull() {
        // Arrange
        SudokuRow row = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));

        // Act & Assert
        assertFalse(row.equals(null), "An object should not be equal to null");
    }

    @Test
    void testEqualsDifferentClass() {
        // Arrange
        SudokuRow row = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));
        String otherClassObject = "I am not a SudokuRow";

        // Act & Assert
        assertFalse(row.equals(otherClassObject), "An object should not be equal to an instance of a different class");
    }

    @Test
    void testEqualsEqualObjects() {
        // Arrange
        SudokuRow row1 = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));
        SudokuRow row2 = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));

        // Act & Assert
        assertTrue(row1.equals(row2), "Two objects with the same fields should be equal");
    }

    @Test
    void testEqualsDifferentObjects() {
        // Arrange
        SudokuRow row1 = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));
        SudokuRow row2 = new SudokuRow(Arrays.asList(new SudokuField(4), new SudokuField(5), new SudokuField(6)));

        // Act & Assert
        assertFalse(row1.equals(row2), "Two objects with different fields should not be equal");
    }

    @Test
    void testToStringContainsClassName() {
        // Arrange
        SudokuRow row = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));

        // Act
        String result = row.toString();

        // Assert
        assertTrue(result.contains("SudokuRow"), "toString should include the class name");
    }

    @Test
    void testToString() {
        // Arrange
        List<SudokuField> fields = Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3));
        SudokuBox box = new SudokuBox(fields);

        // Act
        String result = box.toString();

        // Assert
        String expected = "SudokuBox[Box=[SudokuField[value=1], SudokuField[value=2], SudokuField[value=3]]]";
        assertEquals(expected, result, "toString() should return correctly formatted string");
    }
}
