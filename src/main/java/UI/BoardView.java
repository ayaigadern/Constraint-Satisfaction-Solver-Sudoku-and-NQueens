package UI;
import Listeners.StepListener;
import Model.NQueensBoard;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardView extends GridPane implements StepListener {

    private int N;
    private StackPane[][] cells;
    private Rectangle[][] cellRects;
    private final double MAX_CELL_SIZE = 50;
    private final double MAX_BOARD_SIZE = 500;
    private double cellSize;
    private Image queenImage;
    private final Color lightColor = Color.web("#D0D6B3");
    private final Color darkColor = Color.web("#AAAE7F");

    public BoardView(NQueensBoard board) {
        this.N = board.getN();
        cells = new StackPane[N][N];
        cellRects = new Rectangle[N][N];
        queenImage = new Image(getClass().getResourceAsStream("/icons/chess-queen.png"));


        cellSize = Math.min(MAX_BOARD_SIZE / N, MAX_CELL_SIZE);

        setHgap(0);
        setVgap(0);
        setPadding(Insets.EMPTY);
        setAlignment(javafx.geometry.Pos.CENTER);

        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                Rectangle rect = new Rectangle(cellSize, cellSize);
                rect.setFill((row + col) % 2 == 0 ? lightColor : darkColor);
                rect.setStroke(Color.GRAY);

                StackPane cellStack = new StackPane();
                cellStack.getChildren().add(rect);

                add(cellStack, col, row);
                setHalignment(cellStack, HPos.CENTER);
                setValignment(cellStack, VPos.CENTER);

                cells[row][col] = cellStack;
                cellRects[row][col] = rect;
            }
        }
    }

    public double getBoardSize() {
        return cellSize * N;
    }

    public void setCellSize(double size) {
        this.cellSize = size;
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                cellRects[row][col].setWidth(cellSize);
                cellRects[row][col].setHeight(cellSize);
            }
        }
    }

    @Override
    public void onStep(int row, int col, int value, boolean isBacktracking) {
        Platform.runLater(() -> {
            StackPane cellStack = cells[row][col];
            cellStack.getChildren().removeIf(node -> node instanceof ImageView);

            if (value == 1) {
                ImageView queenView = new ImageView(queenImage);
                queenView.setFitWidth(cellSize);
                queenView.setFitHeight(cellSize);
                cellStack.getChildren().add(queenView);
            } else if (value == 0) {
                cellRects[row][col].setFill((row + col) % 2 == 0 ? lightColor : darkColor);
            }
        });
    }
}
