package Model.Sudoku;

import Listeners.SudokuCellListener;
import java.util.Random;
import Algorithms.Sudoku;

public class SudokuBoard {

    // --- The board ---
    private final SudokuCell[][] board = new SudokuCell[9][9];
    private final SudokuCellListener cellListener;

    // === Constructor ===
    public SudokuBoard(SudokuCellListener cellListener) {
        this.cellListener = cellListener;
        // initialize empty cells and attach listener
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                board[r][c] = new SudokuCell(r, c);
                if (this.cellListener != null) {
                    board[r][c].addListener(this.cellListener);
                }
            }
        }
        initializeBoard();
    }

    public SudokuBoard(int[][] mat, SudokuCellListener cellListener) {
        this.cellListener = cellListener;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (mat[r][c] != 0)
                    board[r][c] = new SudokuCell(r, c, mat[r][c]);
                else
                    board[r][c] = new SudokuCell(r, c);
                if (this.cellListener != null) {
                    board[r][c].addListener(this.cellListener);
                }
            }
        }
    }

    // === Getters ===
    public SudokuCell getCell(int r, int c) {
        return board[r][c];
    }

    public void initializeBoard() {

        // Step 1: Generate a complete solved Sudoku board
        fillBoardCompletely();

        // Step 2: Randomly remove k cells to create the puzzle
        Random rand = new Random();
        int k = rand.nextInt(40, 60); // Number of cells to keep filled
        int toRemove = 81 - k; // Number of cells to empty

        int removed = 0;
        while (removed < toRemove) {
            int r = rand.nextInt(9);
            int c = rand.nextInt(9);

            // Skip if already empty
            if (board[r][c].getValue() == 0)
                continue;

            // Remove the value by replacing with an empty cell
            board[r][c] = new SudokuCell(r, c);
            if (this.cellListener != null) {
                board[r][c].addListener(this.cellListener);
            }
            removed++;
        }
    }

    private void fillBoardCompletely() {
        // Fill the board using a simple solving algorithm with randomization
        fillBoardRecursively(0, 0);
    }

    private boolean fillBoardRecursively(int row, int col) {
        // Move to next row if we've filled all columns
        if (col == 9) {
            return fillBoardRecursively(row + 1, 0);
        }

        // Base case: board is completely filled
        if (row == 9) {
            return true;
        }

        // Create a randomized list of numbers 1-9
        Random rand = new Random();
        int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        // Shuffle the numbers array for randomization
        for (int i = numbers.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = numbers[i];
            numbers[i] = numbers[j];
            numbers[j] = temp;
        }

        // Try each number in random order
        Sudoku validator = new Sudoku(this);
        for (int num : numbers) {
            if (validator.isValid(board[row][col], num)) {
                board[row][col] = new SudokuCell(row, col, num);
                if (this.cellListener != null) {
                    board[row][col].addListener(this.cellListener);
                }

                if (fillBoardRecursively(row, col + 1)) {
                    return true;
                }

                // Backtrack if needed (though this shouldn't happen often with randomization)
                board[row][col] = new SudokuCell(row, col);
                if (this.cellListener != null) {
                    board[row][col].addListener(this.cellListener);
                }
            }
        }

        return false;
    }

}
