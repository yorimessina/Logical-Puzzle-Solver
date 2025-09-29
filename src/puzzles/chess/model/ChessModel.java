package puzzles.chess.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

/**
 * The model for the chess puzzle
 *
 * @author Chloe Nuzillat
 */
public class ChessModel {
    /** the collection of observers of this model */
    private final List<Observer<ChessModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private ChessConfig currentConfig;
    private String filename;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean selecting = false;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<ChessModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }

    /**
     * Gets teh current chess configuration
     *
     * @return the chess configuration
     */
    public ChessConfig getCurrentConfig() {
        return currentConfig;
    }

    /**
     * Gives the user a hint
     */
    public void hint() {
        Solver solver = new Solver();
        List<Configuration> path = solver.bfs(currentConfig);
        if (path != null && path.size() > 1) {
            currentConfig = (ChessConfig) path.get(1);
            alertObservers("Next step!");
        }
        else {
            alertObservers("No solution");
        }
    }

    /**
     * Loads a chess board
     *
     * @param filename the file
     */
    public void load(String filename) throws FileNotFoundException {
        File file = new File(filename);
        if (!file.exists()) {
            alertObservers("Failed to load: " + filename);
            return;
        }
        try {
            this.filename = filename;
            this.currentConfig = new ChessConfig(filename);
            alertObservers("Loaded: " + filename);
        } catch (Exception e) {
            this.currentConfig = null;
            alertObservers("Failed to load puzzle: " + e.getMessage());
        }
    }

    /**
     * Resets the board to its original state
     */
    public void reset() {
        if (filename != null) {
            try {
                this.currentConfig = new ChessConfig(filename);
                alertObservers("Puzzle reset!");
            } catch (Exception e) {
                this.currentConfig = null;
                alertObservers("Failed to reset puzzle: " + e.getMessage());
            }
        } else {
            alertObservers("No puzzle loaded");
        }
    }

    /**
     * Selects a piece to move or to be moved to
     *
     * @param row the row of the piece
     * @param col the column of the piece
     */
    public void select(int row, int col) {
        if (!selecting) {
            if (currentConfig.hasPieceAt(row, col)) {
                selectedRow = row;
                selectedCol = col;
                selecting = true;
                alertObservers("Selected (" + row + ", " + col + ")");
            } else {
                alertObservers("Invalid selection (" + row + ", " + col + ")");
            }
        } else {
            ChessConfig next = currentConfig.makeMove(selectedRow, selectedCol, row, col);
            if (next != null) {
                currentConfig = next;
                alertObservers("Captured from (" + selectedRow + ", " + selectedCol + ") to (" + row + ", " + col
                        + ")");
            } else {
                alertObservers("Can't capture from (" + selectedRow + ", " + selectedCol + ") to (" + row + ", " +
                        col + ")");
            }
            selecting = false;
        }
    }

    /**
     * Quits the program
     */
    public void quit() {
        System.exit(0);
    }

    /**
     * Gets the current chess board
     *
     * @return the current board
     */
    public String[][] getBoard() {
        return this.currentConfig.getBoard();
    }
}
