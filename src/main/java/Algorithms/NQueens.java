package Algorithms;

import Listeners.StepListener;
import Model.NQueensBoard;

public class NQueens implements Algorithm {

    private int N;
    private NQueensBoard board;
    private StepListener listener;
    private int delay = 100;

    public NQueens(int N) {
        this.N = N;
        this.board = new NQueensBoard(N);
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public void setStepListener(StepListener listener) {

        this.listener = listener;
    }

    @Override
    public void run() {
        solve(0);
    }

    // Backtracking solver
    private boolean solve(int row) {
        if (row == N) {
            return true;
        }

        for (int col = 0; col < N; col++) {
            if (isSafe(row, col)) {
                board.setQueenPosition(row, col);
                notifyStep(row, col, false);

                if (solve(row + 1)) return true;

                // backtrack
                board.setQueenPosition(row, -1);
                notifyStep(row, col, true);
            }
        }

        return false;
    }

    // Check if placing a queen is safe
    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            int queenCol = board.getQueenPosition(i);
            if (queenCol == col || Math.abs(queenCol - col) == row - i) return false;
        }
        return true;
    }

    // Notify UI about each step
    private void notifyStep(int row, int col, boolean isBacktracking) {
        if (listener != null) {
            listener.onStep(row, col, isBacktracking ? 0 : 1, isBacktracking);
        }
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public NQueensBoard getBoard() {
        return board;
    }
}

