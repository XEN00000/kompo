package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver {

    @Override
    public void solve(SudokuBoard board) {
        if (board == null) {
            throw new IllegalArgumentException("SudokuBoard cannot be null");
        }
        solveBoard(board, 0, 0);
    }

    private boolean solveBoard(SudokuBoard board, int row, int col) {
        if (row == 9) {
            return true;  // Plansza wypełniona
        }

        if (board.get(row, col) != 0) {
            return solveNextCell(board, row, col);
        }

        List<Integer> numbers = generateShuffledNumbers();

        for (int num : numbers) {
            if (isSafe(board, row, col, num)) {
                board.set(row, col, num);  // Ustawiamy liczbę

                if (solveNextCell(board, row, col)) {
                    return true;
                }

                board.set(row, col, 0);  // Backtrack
            }
        }

        return false;  // Brak poprawnej liczby dla tej komórki
    }


    private boolean solveNextCell(SudokuBoard board, int row, int col) {
        if (col == 8) {
            return solveBoard(board, row + 1, 0);
        } else {
            return solveBoard(board, row, col + 1);
        }
    }

    private boolean isSafe(SudokuBoard board, int row, int col, int num) {
        // Czy liczba nie występuje w wierszu/kolumnie i bloku 3x3
        return !isInRow(board, row, num)
                && !isInCol(board, col, num)
                && !isInBox(board, row - row % 3, col - col % 3, num);
    }

    // Sprawdza czy cyfra występuje już w rzędzie
    private boolean isInRow(SudokuBoard board, int row, int num) {
        for (int col = 0; col < 9; col++) {
            if (board.get(row, col) == num) {
                return true;
            }
        }
        return false;
    }

    // Sprawdza czy cyfra występuje już w kolumnie
    private boolean isInCol(SudokuBoard board, int col, int num) {
        for (int row = 0; row < 9; row++) {
            if (board.get(row, col) == num) {
                return true;
            }
        }
        return false;
    }

    // Sprawdza czy cyfra występuje już w kwadracie
    private boolean isInBox(SudokuBoard board, int startRow, int startCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board.get(row + startRow, col + startCol) == num) {
                    return true;
                }
            }
        }
        return false;
    }

    // Generuje listę losowych liczb
    private static List<Integer> generateShuffledNumbers() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers;
    }
}