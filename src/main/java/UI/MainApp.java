package UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Create buttons
        Button sudokuBtn = new Button("Sudoku");
        Button nqueenBtn = new Button("N-Queens");

        // Colors matching UI.constants.Colors
        String backgroundColor = "#1B2517";
        String btnNormalColor = "#D0D6B3";
        String btnHoverColor = "#AAAE7F";
        String btnTextColor = "#1B2517";

        String btnStyle = "-fx-font-size: 18px; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 15px; " +
                "-fx-text-fill: " + btnTextColor + "; " +
                "-fx-background-color: " + btnNormalColor + ";" +
                "-fx-font-weight: bold;";

        String btnHoverStyle = "-fx-font-size: 18px; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 15px; " +
                "-fx-text-fill: " + btnTextColor + "; " +
                "-fx-background-color: " + btnHoverColor + ";" +
                "-fx-font-weight: bold;";

        sudokuBtn.setStyle(btnStyle);
        nqueenBtn.setStyle(btnStyle);

        sudokuBtn.setOnMouseEntered(e -> sudokuBtn.setStyle(btnHoverStyle));
        sudokuBtn.setOnMouseExited(e -> sudokuBtn.setStyle(btnStyle));
        nqueenBtn.setOnMouseEntered(e -> nqueenBtn.setStyle(btnHoverStyle));
        nqueenBtn.setOnMouseExited(e -> nqueenBtn.setStyle(btnStyle));

        sudokuBtn.setOnAction(e -> new UI.Sudoku.SolverWindow());
        nqueenBtn.setOnAction(e -> new UI.NQueens.SolverWindow());

        VBox root = new VBox(30, sudokuBtn, nqueenBtn);
        root.setStyle("-fx-background-color: " + backgroundColor + "; -fx-alignment: center; -fx-padding: 50px;");

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Constraint Satisfaction Solver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
