package UI.Sudoku;

import Listeners.SudokuCellListener;
import Model.Sudoku.*;
import UI.constants.Colors;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SudokuColorChanger implements SudokuCellListener {

    private Rectangle rectangle;
    private Text text;

    public SudokuColorChanger(Rectangle rectangle, Text text) {
        this.rectangle = rectangle;
        this.text = text;
    }

    @Override
    public void onCellChange(SudokuCell cell) {
        System.out.println("Cell changed: (" + cell.getRow() + "," + cell.getCol() + ") = " + cell.getValue()
                + " state=" + cell.getState());
        Platform.runLater(() -> {
            // Update background color
            Color bgColor = getColorForCell(cell);
            rectangle.setFill(bgColor);

            // Update text value and color
            int value = cell.getValue();
            if (value != 0) {
                text.setText(String.valueOf(value));
                // Fixed cells keep BLACK color, others get WHITE for visibility
                if (cell.isFixed()) {
                    text.setFill(Colors.FIXED);
                } else {
                    text.setFill(Color.WHITE);
                }
            } else {
                text.setText("");
            }
        });
    }

    private Color getColorForCell(SudokuCell cell) {
        // Fixed cells don't change background color - keep original alternating pattern
        // We only change background for non-fixed cells based on their state
        if (!cell.isFixed()) {
            switch (cell.getState()) {
                case CURRENTLY_TESTING:
                    return Colors.TESTING;
                case VALID:
                    return Colors.VALID;
                case CONFLICT:
                    return Colors.CONFLICT;
                case DEFAULT:
                    // Restore the alternating pattern for DEFAULT state
                    return getOriginalBackgroundColor(cell.getRow(), cell.getCol());
            }
        }

        // For fixed cells, keep the original alternating pattern background
        return getOriginalBackgroundColor(cell.getRow(), cell.getCol());
    }

    private Color getOriginalBackgroundColor(int row, int col) {
        // Recreate the same alternating pattern logic from BoardView
        return ((row / 3 + col / 3) % 2 == 0) ? Colors.lightColor : Colors.darkColor;
    }
}
