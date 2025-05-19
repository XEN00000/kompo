package org.example;

import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuFieldTest {

    @Test
    public void testSetFieldValueValidRange() {
        SudokuField field = new SudokuField();

        // Testowanie wartości w prawidłowym zakresie (0-9)
        for (int value = 0; value <= 9; value++) {
            field.setFieldValue(value);
            assertEquals(value, field.getFieldValue(), "Powinno ustawić wartość " + value + " w org.sudoku.SudokuField");
        }
    }

    @Test
    public void testSetFieldValueThrowsExceptionForNegativeValue() {
        SudokuField field = new SudokuField();

        // Testowanie wartości ujemnej
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            field.setFieldValue(-1);
        });
        assertEquals("Value must be between 0 and 9", exception.getMessage());
    }

    @Test
    public void testSetFieldValueThrowsExceptionForValueGreaterThanNine() {
        SudokuField field = new SudokuField();

        // Testowanie wartości większej niż 9
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            field.setFieldValue(10);
        });
        assertEquals("Value must be between 0 and 9", exception.getMessage());
    }

    @Test
    public void testRemovePropertyChangeListener() {
        SudokuField field = new SudokuField();

        // Tworzymy listener, który modyfikuje wartość zmiennej, gdy zostanie wywołany
        PropertyChangeListener listener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                fail("Listener shouldn't be triggered after removal");
            }
        };

        // Dodajemy listener, a następnie go usuwamy
        field.addPropertyChangeListener(listener);
        field.removePropertyChangeListener(listener);

        // Zmieniamy wartość i upewniamy się, że listener nie został wywołany
        field.setFieldValue(5);
    }

    @Test
    void testEqualsWithSameObject() {
        // Arrange
        SudokuRow row = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));

        // Act & Assert
        assertTrue(row.equals(row), "equals should return true when comparing the object with itself");
    }

    @Test
    void testEqualsWithNull() {
        // Arrange
        SudokuRow row = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));

        // Act & Assert
        assertFalse(row.equals(null), "equals should return false when comparing with null");
    }

    @Test
    void testEqualsWithDifferentClass() {
        // Arrange
        SudokuRow row = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));

        // Act & Assert
        assertFalse(row.equals("Not a org.sudoku.SudokuRow"), "equals should return false when comparing with a different class");
    }

    @Test
    void testEqualsWithDifferentData() {
        // Arrange
        SudokuRow row1 = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));
        SudokuRow row2 = new SudokuRow(Arrays.asList(new SudokuField(4), new SudokuField(5), new SudokuField(6)));

        // Act & Assert
        assertFalse(row1.equals(row2), "equals should return false for objects with different data");
    }

    @Test
    void testEqualsWithSameData() {
        // Arrange
        SudokuRow row1 = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));
        SudokuRow row2 = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));

        // Act & Assert
        assertTrue(row1.equals(row2), "equals should return true for objects with the same data");
    }

    @Test
    void testEqualsSymmetricProperty() {
        // Arrange
        SudokuRow row1 = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));
        SudokuRow row2 = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));

        // Act & Assert
        assertTrue(row1.equals(row2) && row2.equals(row1), "equals should be symmetric");
    }

    @Test
    void testEqualsWithDifferentSizes() {
        // Arrange
        SudokuRow row1 = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2), new SudokuField(3)));
        SudokuRow row2 = new SudokuRow(Arrays.asList(new SudokuField(1), new SudokuField(2)));

        // Act & Assert
        assertFalse(row1.equals(row2), "equals should return false for objects with different sizes");
    }

    @Test
    void testDeepCopy() throws CloneNotSupportedException {
        SudokuField original = new SudokuField(5);
        SudokuField copy = original.clone();

        // Sprawdzamy, czy kopia i oryginał to różne obiekty
        assertNotSame(original, copy, "Kopia powinna być innym obiektem niż oryginał");

        // Sprawdzamy, czy pola są takie same
        assertEquals(original.getFieldValue(), copy.getFieldValue(), "Wartości w kopii i oryginale powinny być takie same");

        // Modyfikujemy kopię i sprawdzamy, czy oryginał pozostaje bez zmian
        copy.setFieldValue(8);
        assertNotEquals(original.getFieldValue(), copy.getFieldValue(), "Zmiana w kopii nie powinna wpływać na oryginał");
    }

    @Test
    void testCompareToEqualFields() {
        SudokuField field1 = new SudokuField(5);
        SudokuField field2 = new SudokuField(5);

        assertEquals(0, field1.compareTo(field2), "Porównywane pola o tej samej wartości powinny zwracać 0");
    }

    @Test
    void testCompareToLesserField() {
        SudokuField field1 = new SudokuField(3);
        SudokuField field2 = new SudokuField(5);

        assertTrue(field1.compareTo(field2) < 0, "Mniejsze pole powinno zwracać wartość ujemną");
    }

    @Test
    void testCompareToGreaterField() {
        SudokuField field1 = new SudokuField(7);
        SudokuField field2 = new SudokuField(5);

        assertTrue(field1.compareTo(field2) > 0, "Większe pole powinno zwracać wartość dodatnią");
    }

    @Test
    void testCompareToThrowsExceptionForNull() {
        SudokuField field1 = new SudokuField(5);

        assertThrows(NullPointerException.class, () -> field1.compareTo(null),
                "Metoda compareTo powinna rzucić NullPointerException, jeśli obiekt porównywany jest null");
    }
}
