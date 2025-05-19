package org.example;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class SudokuRow extends SudokuElement implements Cloneable {
    public SudokuRow(List<SudokuField> fields) {
        super(fields);
    }

    @Override
    public SudokuRow clone() {
        try {
            SudokuRow clonedRow = (SudokuRow) super.clone();
            // Głębokie kopiowanie listy pól
            List<SudokuField> clonedFields = new ArrayList<>();
            for (SudokuField field : this.fields) {
                clonedFields.add(field.clone());
            }
            clonedRow.fields = clonedFields;
            return clonedRow;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning not supported", e);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("Row", fields).toString();
    }
}
