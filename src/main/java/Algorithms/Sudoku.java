package Algorithms;

import Listeners.StepListener;
import Model.Sudoku.SudokuBoard;
import Model.Sudoku.SudokuCell;
import Model.Sudoku.SudokuCellState;

public class Sudoku implements Algorithm {

    private SudokuBoard board;

    private int delay = 20;

    public Sudoku(SudokuBoard board) {
        this.board = board;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public void run() {
        boolean solved = solve(0, 0);
        // Store the result so it can be checked later
        this.solvable = solved;
    }

    private boolean solvable = false;

    public boolean isSolvable() {
        return solvable;
    }

    @Override
    public void setStepListener(StepListener listener) {
    }

    private boolean solve(int row, int col) {
        // base case: Reached nth column of the last row
        if (row == 8 && col == 9)
            return true;

        // If last column of the row go to the next row
        if (col == 9) {
            row++;
            col = 0;
        }

        SudokuCell cell = board.getCell(row, col);
        System.out.println(row + " " + col);
        System.out.println(cell.isFixed());

        // If cell is already occupied then move forward
        if (cell.getValue() != 0)
            return solve(row, col + 1);

        for (int num = 1; num <= 9; num++) {
            cell.setValue(num);
            cell.setState(SudokuCellState.CURRENTLY_TESTING);
            sleep();

            // If it is safe to place num at current position
            if (isValid(cell, num)) {
                cell.setState(SudokuCellState.VALID);
                sleep();
                if (solve(row, col + 1)) {
                    System.out.println("for row, col, the best value is: ");
                    System.out.println(cell.getValue());
                    return true;
                }

                // Backtrack: this value didn't lead to solution
                cell.setState(SudokuCellState.CONFLICT);
                sleep();
            } else {
                // Invalid placement - mark as conflict
                cell.setState(SudokuCellState.CONFLICT);
                sleep();
            }

            // Reset cell before trying next value
            cell.setValue(0);
            cell.setState(SudokuCellState.DEFAULT);
            sleep();
        }

        return false;

    }

    private void sleep() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isValid(SudokuCell cell, int value) {
        // value must be between 1 and 9
        if (value < 1 || value > 9)
            return false;

        int row = cell.getRow();
        int col = cell.getCol();

        // Check row
        for (int c = 0; c < 9; c++) {
            if (c == col)
                continue;
            SudokuCell other = board.getCell(row, c);
            if (other.getValue() == value)
                return false;
        }

        // Check column
        for (int r = 0; r < 9; r++) {
            if (r == row)
                continue;
            SudokuCell other = board.getCell(r, col);
            if (other.getValue() == value)
                return false;
        }

        // Check 3x3 subgrid
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;
        for (int r = boxRowStart; r < boxRowStart + 3; r++) {
            for (int c = boxColStart; c < boxColStart + 3; c++) {
                if (c == col && r == row)
                    continue;
                SudokuCell other = board.getCell(r, c);
                if (other.getValue() == value)
                    return false;
            }
        }

        return true;
    }

}
