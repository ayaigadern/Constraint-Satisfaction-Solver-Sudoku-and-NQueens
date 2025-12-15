# Constraint Satisfaction Solver — Sudoku & N-Queens

<<<<<<< HEAD
Java/Maven project demonstrating **backtracking-based constraint satisfaction solvers**
for **Sudoku** and **N-Queens**.

## Features
- Sudoku solver (9×9)
- N-Queens solver (configurable N)
- Classical backtracking with constraint checking
- Listener-based step observation
- Solver logic independent of UI

## Requirements
- Java 11+
- Maven 3.6+

## Build
```bash
mvn clean package
mvn javafx:run
=======
A Java/JavaFX application that demonstrates constraint-satisfaction solvers implemented with backtracking. The project provides two visual solvers:

- Sudoku: generates a puzzle and solves it step-by-step, visualizing attempts and conflicts.
- N-Queens: places N queens on an N×N board using backtracking with step visualization.

**Key points**

- Language: Java 17
- UI: JavaFX
- Build: Maven

**Features**

- Interactive GUI with buttons to open Sudoku or N-Queens solver windows.
- Visual step-by-step solving and backtracking highlighting.
- Randomized Sudoku puzzle generation (creates a complete board then removes cells).
- Clean, minimal code structure separating Algorithms, Model, Listeners, and UI.

**Demo**

Watch the solver in action:

<video width="640" height="480" controls>
  <source src="src/main/resources/vid.mp4" type="video/mp4">
  Your browser does not support the video tag.
</video>

**How it works**

- Both solvers use classical backtracking algorithms:
	- `Algorithms.Sudoku` tries values 1–9 for each empty cell and marks cells as `CURRENTLY_TESTING`, `VALID`, or `CONFLICT`.
	- `Algorithms.NQueens` places queens row-by-row and backtracks when conflicts occur.
- The `Model` package holds board state and notifies UI listeners about changes so the JavaFX views can update in real time.

**Build & Run**

Requirements:

- Java 17 JDK
- Maven

Build

```powershell
# Build the project and run the JavaFX app
mvn clean package

# Run
mvn javafx:run
```

This uses the configured `javafx-maven-plugin` with `UI.MainApp` as the main class. You can also import the project into your IDE and run the `UI.MainApp` class as a JavaFX application.


**Project structure (important files)**

- `src/main/java/Algorithms/Algorithm.java` — algorithm interface used by solvers.
- `src/main/java/Algorithms/NQueens.java` — N-Queens backtracking solver and notifier.
- `src/main/java/Algorithms/Sudoku.java` — Sudoku solver (backtracking) and validator.
- `src/main/java/Model/NQueensBoard.java` — simple board representation for N-Queens.
- `src/main/java/Model/Sudoku/SudokuBoard.java` — Sudoku board generator and container.
- `src/main/java/Model/Sudoku/SudokuCell.java` — cell model with listener notification.
- `src/main/java/Model/Sudoku/SudokuCellState.java` — cell states used for visualization.
- `src/main/java/UI/MainApp.java` — application entry; opens solver windows.
- `src/main/java/UI/Sudoku/*` and `src/main/java/UI/NQueens/*` — solver windows and board views.

**Notes & suggestions**

- Tweak solver delays via `setDelay(...)` in `Algorithms.Sudoku` and `Algorithms.NQueens` for faster/slower visualization.
- The Sudoku generator uses a randomized fill + removal strategy; puzzles may vary in difficulty.
- Some classes print debug output (e.g., `System.out.println` in `Algorithms.Sudoku`) — these can be removed or replaced by a logger.
>>>>>>> 4bca284 (Update README file)
