package Model;

public class NQueensBoard {

    private int[] queens; // queens[row] = column of queen
    private int N;

    public NQueensBoard(int N) {
        this.N = N;
        queens = new int[N];
        for (int i = 0; i < N; i++) queens[i] = -1; // -1 = empty
    }

    public int   getQueenPosition(int row) {
        return queens[row];
    }

    public void setQueenPosition(int row, int col) {
        queens[row] = col;
    }

    public int getN() {
        return N;
    }
}
