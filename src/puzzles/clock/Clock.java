package puzzles.clock;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.List;

/**
 * Main class for the clock puzzle.
 *
 * @author Chloe Nuzillat
 */
public class Clock {
    /**
     * Run an instance of the clock puzzle.
     *
     * @param args [0]: the number of hours in the clock;
     *             [1]: the starting hour;
     *             [2]: the finish hour.
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println(("Usage: java Clock hours start finish"));
        } else {
            System.out.println("Hours: " + args[0] + ", Start: " + args[1] + ", End: " + args[2]);
            ClockConfig startConfig = new ClockConfig(Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                    Integer.parseInt(args[0]));
            Solver solver = new Solver();
            List<Configuration> path = solver.bfs(startConfig);
            System.out.println("Total configs: " + solver.getTotalConfigs());
            System.out.println("Unique configs: " + solver.getUniqueConfigs());
            if (path == null) {
                System.out.println("No solution");
            } else {
                for (int i = 0; i < path.size(); i++) {
                    System.out.println("Step " + i + ": " + path.get(i));
                }
            }
        }
    }
}
