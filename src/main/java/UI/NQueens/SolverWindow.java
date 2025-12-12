package UI.NQueens;
import Algorithms.NQueens;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
    private NQueens solver;
    private Thread solverThread;
    private int speed = 100;
    private final int MAX_N = 26;

    public SolverWindow() {
        setTitle("N-Queens Solver");

        // --- Root VBox ---
        VBox root = new VBox();
        root.setBackground(new Background(new BackgroundFill(Color.web("#1B2517"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.setPadding(new Insets(20, 10, 20, 10));

        // --- Top : Board size input ---
        Label sizeLabel = new Label("Board size (N):");
        sizeLabel.setFont(Font.font("Arial", 18));
        sizeLabel.setTextFill(Color.web("#D0D6B3"));
        TextField sizeInput = new TextField("8");
        sizeInput.setPrefWidth(50);

        HBox inputBox = new HBox(5, sizeLabel, sizeInput);
        inputBox.setAlignment(Pos.CENTER);

        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);
        messageLabel.setFont(Font.font(14));
        messageLabel.setAlignment(Pos.CENTER);

        VBox topBox = new VBox(5, inputBox, messageLabel);
        topBox.setAlignment(Pos.CENTER);

        // --- Center : Board ---
        solver = new NQueens(8);
        boardView = new BoardView(solver.getBoard());
        solver.setStepListener(boardView);
        VBox boardContainer = new VBox(boardView);
        boardContainer.setAlignment(Pos.CENTER);

        // --- Bottom : Controls ---
        HBox bottomBox = new HBox(20);
        bottomBox.setAlignment(Pos.CENTER);
        Button startBtn = new Button("Start");
        Button resetBtn = new Button("Reset");
        Slider speedSlider = new Slider(10, 500, 100);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        Label speedLabel = new Label("Speed:");
        speedLabel.setTextFill(Color.web("#D0D6B3"));
        styleButton(startBtn);
        styleButton(resetBtn);
        bottomBox.getChildren().addAll(startBtn, resetBtn, speedLabel, speedSlider);

        root.getChildren().addAll(topBox, boardContainer, bottomBox);

        Platform.runLater(() -> adjustWindowSize(boardView.getBoardSize(), 8));

        // --- Actions ---
        startBtn.setOnAction(e -> runSolver(sizeInput, boardContainer, messageLabel));
        resetBtn.setOnAction(e -> resetSolver(sizeInput, boardContainer, messageLabel));

        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            speed = newVal.intValue();
            if (solver != null) solver.setDelay(speed);
        });

        Scene scene = new Scene(root);
        setScene(scene);
        show();
    }

    private void runSolver(TextField sizeInput, VBox boardContainer, Label messageLabel) {
        int N;
        try {
            N = Integer.parseInt(sizeInput.getText());

            if (N <= 0) {
                messageLabel.setText("Board size must be greater than 0");
                boardContainer.getChildren().clear();
                adjustWindowSize(0, N);
                return;
            }
            if (N == 2 || N == 3) {
                messageLabel.setText("No solution exists for N = " + N);
                boardContainer.getChildren().clear();
                adjustWindowSize(0, N);
                return;
            }

            if (N > MAX_N) {
                messageLabel.setText("Maximum board size is " + MAX_N);
                boardContainer.getChildren().clear();
                adjustWindowSize(0, N);
                return;
            }

        } catch (NumberFormatException ex) {
            messageLabel.setText("Invalid input");
            boardContainer.getChildren().clear();
            adjustWindowSize(0, 0);
            return;
        }

        messageLabel.setText("");

        if (solverThread != null && solverThread.isAlive()) solverThread.interrupt();

        solver = new NQueens(N);
        solver.setDelay(speed);
        boardView = new BoardView(solver.getBoard());

        solver.setStepListener(boardView);

        boardContainer.getChildren().clear();
        boardContainer.getChildren().add(boardView);

        Platform.runLater(() -> adjustWindowSize(boardView.getBoardSize(), N));

        solverThread = new Thread(solver::run);
        solverThread.start();
    }

    private void resetSolver(TextField sizeInput, VBox boardContainer, Label messageLabel) {
        if (solverThread != null && solverThread.isAlive()) {
            solverThread.interrupt();
        }

        solver = new NQueens(8);
        boardView = new BoardView(solver.getBoard());
        solver.setStepListener(boardView);

        boardContainer.getChildren().clear();
        boardContainer.getChildren().add(boardView);

        sizeInput.setText("8");
        messageLabel.setText("");

        Platform.runLater(() -> adjustWindowSize(boardView.getBoardSize(), 8));
    }

    private void adjustWindowSize(double boardSize, int N) {
        double extraHeight = 190;
        double width;
        double height;

        if (boardSize <= 0) {
            width = 400;
            height = 200;
        } else {
            height = boardSize + extraHeight;

            if (N <= 5) {
                width = 400;
            } else {
                width = boardSize + 80; // board normal
            }
        }

        setWidth(width);
        setHeight(height);
    }


    private void styleButton(Button btn) {
        String normalColor = "#D0D6B3";
        String hoverColor = "#AAAE7F";
        String textColor = "#1B2517";

        btn.setStyle("-fx-background-color: " + normalColor + ";" +
                "-fx-text-fill: " + textColor + ";" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);");

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: " + hoverColor + ";" +
                "-fx-text-fill: " + textColor + ";" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);"));

        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: " + normalColor + ";" +
                "-fx-text-fill: " + textColor + ";" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);"));
    }
}
