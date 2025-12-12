package UI.Sudoku;

import Algorithms.Sudoku;
import Model.Sudoku.SudokuBoard;
import Model.Sudoku.SudokuCell;
import Model.Sudoku.SudokuCellState;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SolverWindow extends Stage {

    private BoardView boardView;
    private Sudoku solver;
    private Thread solverThread;
    private int speed = 20;
    private SudokuBoard sudokuBoard;

    public SolverWindow() {

        // Default matrice that actually has a solution
        int[][] mat = {
                { 3, 0, 6, 5, 0, 8, 4, 0, 0 },
                { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
                { 0, 0, 3, 0, 1, 0, 0, 8, 0 },
                { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
                { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
                { 1, 3, 0, 0, 0, 0, 2, 5, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
                { 0, 0, 5, 2, 0, 6, 3, 0, 0 }
        };

        setTitle("Sudoku Solver");

        // --- Root VBox ---
        VBox root = new VBox();
        root.setBackground(new Background(new BackgroundFill(Color.web("#1B2517"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        root.setPadding(new Insets(20));
        root.setMinSize(500, 500);

        // --- Top : Title ---
        Label titleLabel = new Label("Sudoku Constraint Solver");
        titleLabel.setFont(Font.font("Arial", 24));
        titleLabel.setTextFill(Color.web("#D0D6B3"));

        // --- Center : Board ---
        sudokuBoard = new SudokuBoard(mat, null);
        boardView = new BoardView(sudokuBoard);

        VBox boardContainer = new VBox(boardView);
        boardContainer.setAlignment(Pos.CENTER);

        // --- Bottom : Controls ---
        HBox bottomBox = new HBox(20);
        bottomBox.setAlignment(Pos.CENTER);

        Button startBtn = new Button("Start Solving");
        Button resetBtn = new Button("Reset Board");
        Button newPuzzleBtn = new Button("New Puzzle");

        Slider speedSlider = new Slider(10, 100, 20);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setPrefWidth(150);

        Label speedLabel = new Label("Speed (ms):");
        speedLabel.setTextFill(Color.web("#D0D6B3"));

        styleButton(startBtn);
        styleButton(resetBtn);
        styleButton(newPuzzleBtn);

        bottomBox.getChildren().addAll(startBtn, resetBtn, newPuzzleBtn, speedLabel, speedSlider);

        // --- Status Label ---
        Label statusLabel = new Label("");
        statusLabel.setTextFill(Color.web("#D0D6B3"));
        statusLabel.setFont(Font.font(14));

        root.getChildren().addAll(titleLabel, boardContainer, bottomBox, statusLabel);

        // --- Actions ---
        startBtn.setOnAction(e -> {
            if (solverThread != null && solverThread.isAlive()) {
                statusLabel.setText("Solver is already running!");
                statusLabel.setTextFill(Color.ORANGE);
            } else {
                startSolver(statusLabel);
            }
        });

        resetBtn.setOnAction(e -> {
            if (solverThread != null && solverThread.isAlive()) {
                solverThread.interrupt();
                try {
                    // Wait for the thread to actually stop
                    solverThread.join(1000); // Wait up to 1 second
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            // Clear the status label
            statusLabel.setText("");
            statusLabel.setTextFill(Color.web("#D0D6B3"));
            
            // Keep the same puzzle but reset to initial state
            boardContainer.getChildren().clear();
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    SudokuCell cell = sudokuBoard.getCell(r, c);
                    if (!cell.isFixed()) {
                        cell.setValue(0);
                        cell.setState(SudokuCellState.DEFAULT);
                    }
                }
            }
            boardView = new BoardView(sudokuBoard);
            boardContainer.getChildren().add(boardView);
        });

        newPuzzleBtn.setOnAction(e -> {
            if (solverThread != null && solverThread.isAlive()) {
                solverThread.interrupt();
                try {
                    // Wait for the thread to actually stop
                    solverThread.join(1000); // Wait up to 1 second
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            // Clear the status label
            statusLabel.setText("");
            statusLabel.setTextFill(Color.web("#D0D6B3"));
            
            generateNewPuzzle(boardContainer);
        });

        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            speed = newVal.intValue();
            if (solver != null) {
                solver.setDelay(speed);
            }
        });

        Scene scene = new Scene(root);
        setScene(scene);

        // Adjust window size
        Platform.runLater(() -> {
            setWidth(boardView.getBoardSize() + 100);
            setHeight(boardView.getBoardSize() + 250);
        });

        show();
    }

    private void startSolver(Label statusLabel) {
        statusLabel.setText("Solving...");
        statusLabel.setTextFill(Color.web("#D0D6B3"));

        solver = new Sudoku(sudokuBoard);
        solver.setDelay(speed);

        solverThread = new Thread(() -> {
            solver.run();
            Platform.runLater(() -> {
                if (solver.isSolvable()) {
                    statusLabel.setText("Solved!");
                    statusLabel.setTextFill(Color.web("#229954")); // Green
                } else {
                    statusLabel.setText("No solution exists!");
                    statusLabel.setTextFill(Color.web("#A93226")); // Red
                }
            });
        });
        solverThread.start();
    }

    private void generateNewPuzzle(VBox boardContainer) {
        sudokuBoard = new SudokuBoard(null);
        boardView = new BoardView(sudokuBoard);

        boardContainer.getChildren().clear();
        boardContainer.getChildren().add(boardView);
    }

    private void styleButton(Button btn) {
        String normalColor = "#D0D6B3";
        String hoverColor = "#AAAE7F";
        String textColor = "#1B2517";

        btn.setStyle("-fx-background-color: " + normalColor + ";" +
                "-fx-text-fill: " + textColor + ";" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 10px 20px;" +
                "-fx-background-radius: 5;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);");

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: " + hoverColor + ";" +
                "-fx-text-fill: " + textColor + ";" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 10px 20px;" +
                "-fx-background-radius: 5;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);"));

        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: " + normalColor + ";" +
                "-fx-text-fill: " + textColor + ";" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 10px 20px;" +
                "-fx-background-radius: 5;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);"));
    }
}
