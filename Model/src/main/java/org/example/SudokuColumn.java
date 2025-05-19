package org.example;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class SudokuColumn extends SudokuElement implements Cloneable {
    public SudokuColumn(List<SudokuField> fields) {
        super(fields);
    }

    @Override
    public SudokuColumn clone() {
        try {
            SudokuColumn clonedColumn = (SudokuColumn) super.clone();
            // Głębokie kopiowanie listy pól
            List<SudokuField> clonedFields = new ArrayList<>();
            for (SudokuField field : this.fields) {
                clonedFields.add(field.clone());
            }
            clonedColumn.fields = clonedFields;
            return clonedColumn;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning not supported", e);
        }
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("Column", fields)
                .toString();
    }
}
