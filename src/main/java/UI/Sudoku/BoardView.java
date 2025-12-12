package UI.Sudoku;

import Model.Sudoku.SudokuBoard;
import Model.Sudoku.SudokuCell;
import UI.constants.Colors;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BoardView extends GridPane {

    private static final int GRID_SIZE = 9;
    private static final double CELL_SIZE = 60;
    private static final double BORDER_WIDTH = 2;

    private SudokuBoard board;
    private StackPane[][] cellPanes;
    private Rectangle[][] cellRects;
    private Text[][] cellTexts;

    public BoardView(SudokuBoard board) {
        this.board = board;
        this.cellPanes = new StackPane[GRID_SIZE][GRID_SIZE];
        this.cellRects = new Rectangle[GRID_SIZE][GRID_SIZE];
        this.cellTexts = new Text[GRID_SIZE][GRID_SIZE];

        setupGrid();
        initializeCells();
    }

    private void setupGrid() {
        setHgap(0);
        setVgap(0);
        setPadding(Insets.EMPTY);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: " + toHex(Colors.backgroundColor) + ";");
    }

    private void initializeCells() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                // Create rectangle for cell background
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setFill(getCellBackgroundColor(row, col));
                rect.setStroke(Color.GRAY);
                rect.setStrokeWidth(0.5);

                // Add thicker borders for 3x3 boxes
                if (col % 3 == 0 && col != 0) {
                    rect.setStrokeWidth(BORDER_WIDTH);
                }

                // Create text for cell value
                Text text = new Text();
                text.setFont(Font.font("Arial", FontWeight.BOLD, 24));

                // Get the cell from the board
                SudokuCell cell = board.getCell(row, col);

                // Set initial value and color
                updateCellDisplay(cell, text);

                // Create stack pane to hold rectangle and text
                StackPane cellStack = new StackPane();
                cellStack.getChildren().addAll(rect, text);

                // Add thicker borders between 3x3 boxes
                if (row % 3 == 0 && row != 0) {
                    cellStack.setStyle("-fx-border-color: gray; -fx-border-width: " + BORDER_WIDTH + " 0 0 0;");
                }
                if (col % 3 == 0 && col != 0) {
                    cellStack.setStyle(cellStack.getStyle() + "-fx-border-color: gray; -fx-border-width: 0 0 0 "
                            + BORDER_WIDTH + ";");
                }

                // Register listener for cell changes
                SudokuColorChanger colorChanger = new SudokuColorChanger(rect, text);
                cell.addListener(colorChanger);

                // Add to grid
                add(cellStack, col, row);

                // Store references
                cellPanes[row][col] = cellStack;
                cellRects[row][col] = rect;
                cellTexts[row][col] = text;
            }
        }
    }

    private Color getCellBackgroundColor(int row, int col) {
        // Alternate light and dark colors for better visibility
        return ((row / 3 + col / 3) % 2 == 0) ? Colors.lightColor : Colors.darkColor;
    }

    private void updateCellDisplay(SudokuCell cell, Text text) {
        int value = cell.getValue();
        if (value != 0) {
            text.setText(String.valueOf(value));
            // Fixed cells get FIXED color, others get based on state
            if (cell.isFixed()) {
                text.setFill(Colors.FIXED);
            } else {
                text.setFill(Color.WHITE);
            }
        } else {
            text.setText("");
        }
    }

    public double getBoardSize() {
        return CELL_SIZE * GRID_SIZE;
    }

    private String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
