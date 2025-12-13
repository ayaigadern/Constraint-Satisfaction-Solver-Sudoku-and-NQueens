# Constraint-Satisfaction-Solver — Sudoku & N-Queens

A small Java/Maven application demonstrating constraint-satisfaction problem
solvers (backtracking-based) for Sudoku and N-Queens with a simple GUI.

**Highlights**
- Solve standard 9x9 Sudoku puzzles using a backtracking CSP solver.
- Solve the N-Queens problem for configurable board sizes.
- Step-through visualization and listeners to observe solver progress.

**Requirements**
- Java 11 or newer
- Maven 3.6+

**Build**
To compile the project run:

```bash
mvn clean package
```
To run the application using the JavaFX Maven plugin, use:

```bash
mvn javafx:run
```

(This requires the JavaFX Maven plugin and JavaFX SDK to be available in your
build environment or configured in the project.)

**Quick Usage**
- Launch the application (see Build). The main window provides access to the
	Sudoku and N-Queens solver windows.
- For Sudoku: create or load a puzzle, then start the solver; use the step
	controls to observe the search order and cell assignments.
- For N-Queens: select board size and run the solver; the board view will show
	queen placements during the search.

**Project Structure (key files)**
- `src/main/java/Algorithms` — core solver logic (`Algorithm.java`, `Sudoku.java`, `NQueens.java`)
- `src/main/java/Model` — board and cell models (including `SudokuBoard.java`, `NQueensBoard.java`)
- `src/main/java/UI` — JavaFX UI entry `MainApp.java`, solver windows and views
- `src/main/java/Listeners` — step/listener interfaces used by the UI

**Algorithms & Approach**
The solvers implement classical backtracking search with pruning suitable for
constraint-satisfaction problems. The code is structured so the UI listens to
solver steps and updates the board views for visualization.
