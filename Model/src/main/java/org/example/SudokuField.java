package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serial;
import java.io.Serializable;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {
    @Serial
    private static final long serialVersionUID = 1L; // Zalecany identyfikator dla Serializable

    private int value;

    // Pole transient, bo PropertyChangeSupport nie jest serializowalne
    private transient PropertyChangeSupport support;

    // Konstruktor bezargumentowy
    public SudokuField() {
        initPropertyChangeSupport();
    }

    // Konstruktor z wartością początkową
    public SudokuField(int value) {
        this.value = value;
        initPropertyChangeSupport();
    }

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int newValue) {
        if (newValue < 0 || newValue > 9) {
            throw new IllegalArgumentException("Value must be between 0 and 9");
        }

        int oldValue = this.value;
        this.value = newValue;

        // Powiadamiamy o zmianie wartości. Odbiorcą będzie np. org.sudoku.SudokuBoard
        support.firePropertyChange("fieldValue", oldValue, newValue);
    }

    // Metody modyfikacji (dodawania i usuwania) obserwatorów
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    // Inicjalizacja PropertyChangeSupport
    private void initPropertyChangeSupport() {
        support = new PropertyChangeSupport(this);
    }

    // Wywoływane automatycznie po deserializacji
    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        initPropertyChangeSupport(); // Odtworzenie PropertyChangeSupport
    }

    @Override
    public int compareTo(SudokuField other) {
        return Integer.compare(this.value, other.value);
    }

    @Override
    public SudokuField clone() {
        try {
            return (SudokuField) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("value", value)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SudokuField that = (SudokuField) obj;
        return new EqualsBuilder()
                .append(value, that.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .toHashCode();
    }
}
