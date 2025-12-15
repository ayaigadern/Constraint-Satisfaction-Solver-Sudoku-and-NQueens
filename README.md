# Constraint Satisfaction Solver — Sudoku & N-Queens
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
 	<source src="https://raw.githubusercontent.com/ayaigadern/Constraint-Satisfaction-Solver-Sudoku-and-NQueens/main/src/main/resources/vid.mp4" type="video/mp4">
 	Your browser does not support the video tag.
</video>

If the embedded player doesn't load in the GitHub README preview, open or download the video directly:

[Watch / Download the demo video](https://raw.githubusercontent.com/ayaigadern/Constraint-Satisfaction-Solver-Sudoku-and-NQueens/main/src/main/resources/vid.mp4)

To reliably host and embed the video on GitHub pages (recommended):

1. Move the video into a `docs/` folder (or a `gh-pages` branch) so GitHub Pages can serve it.

```bash
mkdir -p docs
cp src/main/resources/vid.mp4 docs/
git add docs/vid.mp4
git commit -m "Add demo video for GitHub Pages"
git push origin main
```

2. Enable GitHub Pages (Repository Settings → Pages) selecting the `main` branch `/docs` folder (or `gh-pages` branch). The video will then be available at:

```
https://ayaigadern.github.io/Constraint-Satisfaction-Solver-Sudoku-and-NQueens/vid.mp4
```

Update the `<source>` `src` attribute to that URL to embed the playable video in the README.

**How it works**

**Thumbnail embedding (recommended for README)**

To show a clickable thumbnail in the README that opens the video (works reliably in GitHub preview):

1. Add a thumbnail image (e.g. `thumbnail.jpg`) and the video to your repository (recommended: `docs/` folder).

```bash
# create docs folder and copy files
mkdir -p docs
cp src/main/resources/vid.mp4 docs/vid.mp4
# create or copy a thumbnail image to docs/thumbnail.jpg
cp path/to/your/thumbnail.jpg docs/thumbnail.jpg
git add docs/vid.mp4 docs/thumbnail.jpg
git commit -m "Add demo video and thumbnail for README"
git push origin main
```

2. Insert this Markdown into your `README.md` (example already filled for this repository):

```markdown
[![Watch the video](https://raw.githubusercontent.com/ayaigadern/Constraint-Satisfaction-Solver-Sudoku-and-NQueens/main/docs/thumbnail.jpg)](https://raw.githubusercontent.com/ayaigadern/Constraint-Satisfaction-Solver-Sudoku-and-NQueens/main/docs/vid.mp4)
```

Replace `ayaigadern`, repository name, branch (`main`) or paths (`docs/...`) as needed.

**Animated GIF example**

You can also embed an animated GIF directly (no click required). Example syntax (from another repo):

```markdown
![Fuzzy Finder Demo](https://raw.githubusercontent.com/MouadBensafir/Fuzzy-Finder/refs/heads/main/tutorial.gif)
```

Or for this repo (if you add `docs/demo.gif`):

```markdown
![Solver Demo](https://raw.githubusercontent.com/ayaigadern/Constraint-Satisfaction-Solver-Sudoku-and-NQueens/main/docs/demo.gif)
```


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
<<<<<<< HEAD
=======

**Notes & suggestions**

- Tweak solver delays via `setDelay(...)` in `Algorithms.Sudoku` and `Algorithms.NQueens` for faster/slower visualization.
- The Sudoku generator uses a randomized fill + removal strategy; puzzles may vary in difficulty.
- Some classes print debug output (e.g., `System.out.println` in `Algorithms.Sudoku`) — these can be removed or replaced by a logger.
>>>>>>> ff062b0 (Added a Demo)
