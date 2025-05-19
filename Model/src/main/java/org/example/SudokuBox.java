package org.example;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class SudokuBox extends SudokuElement implements Cloneable {
    public SudokuBox(List<SudokuField> fields) {
        super(fields);
    }

    @Override
    public SudokuBox clone() {
        try {
            SudokuBox clonedBox = (SudokuBox) super.clone();
            // Głębokie kopiowanie listy pól
            List<SudokuField> clonedFields = new ArrayList<>();
            for (SudokuField field : this.fields) {
                clonedFields.add(field.clone());
            }
            clonedBox.fields = clonedFields;
            return clonedBox;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning not supported", e);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("Box", fields)
                .toString();
    }
}
