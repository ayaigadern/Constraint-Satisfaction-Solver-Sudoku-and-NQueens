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

        String btnStyle = "-fx-font-size: 18px; -fx-background-radius: 10; -fx-padding: 15px; -fx-text-fill: white; -fx-background-color: #4c47a3;";
        sudokuBtn.setStyle(btnStyle);
        nqueenBtn.setStyle(btnStyle);

        sudokuBtn.setOnMouseEntered(e -> sudokuBtn.setStyle("-fx-background-color: #6c63ff; -fx-font-size:18px; -fx-background-radius:10; -fx-padding:15px; -fx-text-fill:white;"));
        sudokuBtn.setOnMouseExited(e -> sudokuBtn.setStyle(btnStyle));
        nqueenBtn.setOnMouseEntered(e -> nqueenBtn.setStyle("-fx-background-color: #6c63ff; -fx-font-size:18px; -fx-background-radius:10; -fx-padding:15px; -fx-text-fill:white;"));
        nqueenBtn.setOnMouseExited(e -> nqueenBtn.setStyle(btnStyle));

        nqueenBtn.setOnAction(e -> new SolverWindow());

        VBox root = new VBox(30, sudokuBtn, nqueenBtn);
        root.setStyle("-fx-background-color: #1e1e2f; -fx-alignment: center; -fx-padding: 50px;");

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Constraint Satisfaction Solver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
