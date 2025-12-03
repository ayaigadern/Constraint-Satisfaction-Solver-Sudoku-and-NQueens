package Model.Sudoku;

public enum SudokuCellState {
    DEFAULT, // normal either isFixed or not
    CURRENTLY_TESTING, // the solver is testing this cell 
    VALID, // A correct placement so far / final placement at the end 
    CONFLICT // The placement violates a Sudoku rule
}
