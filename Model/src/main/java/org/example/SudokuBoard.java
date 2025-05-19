package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

public class SudokuBoard implements PropertyChangeListener, Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(SudokuBoard.class.getName());
    protected transient SudokuSolver solver; // Transient, ponieważ solver nie musi być serializowany
    private SudokuField[][] board;

    public SudokuBoard() {
        this.solver = new BacktrackingSudokuSolver(); // Ustawienie domyślnego solvera
        board = new SudokuField[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                //int val = (row * 3 + row / 3 + col) % 9 + 1;
                board[row][col] = new SudokuField(0);
                board[row][col].addPropertyChangeListener(this);
            }
        }
    }

    public int get(int row, int col) {
        return board[row][col].getFieldValue();
    }

    public void set(int row, int col, int value) {
        board[row][col].setFieldValue(value);
    }

    public void solveGame() {
        this.solver.solve(this);
    }

    public SudokuField[][] getBoard() {
        return board;
    }

    public void setSolver() {
        this.solver = new BacktrackingSudokuSolver();
    }

    public void printBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(get(row, col) + " ");
            }
            System.out.println();
        }
    }

    public void hideFields(int fieldsToHide) {
        Random random = new Random();
        for (int i = 0; i < fieldsToHide; i++) {
            int row;
            int col;
            do {
                row = random.nextInt(9);
                col = random.nextInt(9);
            } while (this.get(row, col) == 0); // Unikaj ponownego zakrywania tego samego pola

            this.set(row, col, 0); // Zakryj pole
        }
    }

    // Korzystamy z hierachii wyjątków
    public void validateField(int value) throws SudokuInvalidFieldException {
        if (value < 1 || value > 9) {
            throw new SudokuInvalidFieldException();
        }
    }

    public void removeFields(int count) {
        Random rand = new Random();
        int removed = 0;
        while (removed < count) {
            int r = rand.nextInt(9);
            int c = rand.nextInt(9);
            if (board[r][c].getFieldValue() != 0) {
                board[r][c].setFieldValue(0);
                removed++;
            }
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        // Tutaj reagujemy na zmiany wartości w polach.
        // Na przykład można logować zmiany lub sprawdzać warunki wygranej.
        if ("value".equals(evt.getPropertyName())) {
            logger.info("Field changed from " + evt.getOldValue() + " to " + evt.getNewValue());
        }
    }

    @Override
    public SudokuBoard clone() {
        try {
            SudokuBoard clonedBoard = (SudokuBoard) super.clone();

            // Tworzymy nową tablicę, aby uniknąć współdzielenia referencji do tablicy
            clonedBoard.board = new SudokuField[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    clonedBoard.board[i][j] = this.board[i][j].clone(); // Klonowanie każdego pola
                }
            }
            return clonedBoard;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("board", Arrays.deepToString(board)).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SudokuBoard that = (SudokuBoard) obj;
        return new EqualsBuilder().append(board, that.board) // Porównanie tablicy board
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(board).toHashCode();
    }
}
