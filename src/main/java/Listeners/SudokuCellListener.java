package Listeners;

import Model.Sudoku.SudokuCell;

public interface SudokuCellListener {

    /**
     * This listener interface change the SudokuCell behavior based on the state it is in.
     * @param cell the actual cell in Sudoku
     * */
    void onCellChange(SudokuCell cell);
}
