package puzzles.tilt.model;

import puzzles.common.Observer;
import puzzles.common.solver.Solver;
import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Model for PTUI and GUI of Tilt
 *
 * @author Yori Messina
 */
public class TiltModel {
    /** the collection of observers of this model */
    private final List<Observer<TiltModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private TiltConfig currentConfig;
    private String filename;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<TiltModel, String> observer) {
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

    public TiltConfig getCurrentConfig() {
        return currentConfig;
    }

    public void getHint(){
        Solver solver = new Solver();
        List<Configuration> path = solver.bfs(currentConfig);

        if (path != null && path.size() > 1) {
            currentConfig = (TiltConfig) path.get(1);

            if (currentConfig.isSolution()){
                alertObservers("Solved");
            }
            else {
                alertObservers("Advanced to next step");
            }
        }
        else {
            alertObservers("No solution");
        }
    }

    public void load(String filename) throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            this.filename = filename;
            String line = br.readLine();

            int dim = Integer.parseInt(line.strip());
            String[][] board = new String[dim][dim];

            for (int i = 0; i < dim; i++) {
                line = br.readLine();
                System.out.println(line);
                String[] row = line.split(" ");
                board[i] = row;
            }
            this.currentConfig = new TiltConfig(dim, dim, board);
            alertObservers("Loaded: " + filename);
        }
        catch (Exception e) {
            alertObservers("Failed to load: " + e.getMessage());
        }
    }

    public void tilt(String dir){
        TiltConfig newConfig = currentConfig.tilt(dir);

        if (newConfig != null) {
            currentConfig = newConfig;
            alertObservers("Tilted: " + dir);
        }
        else{
            alertObservers("Invalid move");
        }
    }

    public void quit(){
        alertObservers("Quitting...");
        System.exit(0);
    }

    public void reset() throws FileNotFoundException {
        if (filename != null) {
            load(filename);
            alertObservers("Puzzle reset");
        }
        else{
            alertObservers("No file loaded");
        }
    }
}
