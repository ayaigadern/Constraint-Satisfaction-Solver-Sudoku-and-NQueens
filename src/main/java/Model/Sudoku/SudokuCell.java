package Model.Sudoku;

import Listeners.SudokuCellListener;
import java.util.concurrent.CopyOnWriteArrayList;

public class SudokuCell {

    // --- Data Field ---
    private int value; // value of the cell from 1 to 9
    // position in the grid 0-based
    private final int row;
    private final int col;
    // field to check if the value is fixed as initial state of the board;
    private final boolean isFixed;

    // --- State Field ---
    private SudokuCellState state = SudokuCellState.DEFAULT;

    // --- Observers of the cell ---
    private CopyOnWriteArrayList<SudokuCellListener> listeners = new CopyOnWriteArrayList<>();

    // === Constructors ===
    // If the cell is blank
    SudokuCell(int r, int c) {
        this.row = r;
        this.col = c;
        this.isFixed = false;
    }

    SudokuCell(int r, int c, int v) {
        this.row = r;
        this.col = c;
        this.value = v;
        this.isFixed = true;
    }

    // === Getters ===
    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public SudokuCellState getState() {
        return state;
    }

    public int getValue() {
        return value;
    }

    public boolean isFixed() {
        return isFixed;
    }

    // === Listeners Management ===
    public void addListener(SudokuCellListener listener) {
        this.listeners.add(listener);
    }

    public void clearListeners() {
        this.listeners.clear();
    }

    public void notifyListeners() {
        for (SudokuCellListener listener : listeners) {
            listener.onCellChange(this);
        }
    }

    // === Setters ===
    public void setValue(int value) {
        if (this.isFixed)
            return;
        this.value = value;
        notifyListeners();
    }

    public void setState(SudokuCellState state) {
        if (this.isFixed)
            return;
        this.state = state;
        notifyListeners();
    }

}
