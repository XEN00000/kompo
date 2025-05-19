package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SudokuElement {
    protected List<SudokuField> fields;

    public SudokuElement(List<SudokuField> fields) {
        this.fields = fields;
    }

    public boolean verify() {
        Set<Integer> seenValues = new HashSet<>();
        for (SudokuField field : fields) {
            int value = field.getFieldValue();
            if (value != 0) { // Pomijamy puste pola
                if (seenValues.contains(value)) {
                    return false;  // Znaleziono duplikat
                }
                seenValues.add(value);
            }
        }
        return true; // Brak duplikatów element jest poprawny
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("Element", fields)
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
        SudokuRow that = (SudokuRow) obj;
        return new EqualsBuilder()
                .append(fields, that.fields) // Porównanie
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(fields)
                .toHashCode();
    }
}
