package Listeners;

public interface StepListener {
    /**
     * @param row The row of the change
     * @param col The column of the change
     * @param value 1 = placed, 0 = removed
     * @param isBacktracking true if undoing a move
     */
    void onStep(int row, int col, int value, boolean isBacktracking);
}
