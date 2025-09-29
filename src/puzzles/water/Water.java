package puzzles.water;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for the water buckets puzzle.
 *
 * @author Chloe Nuzillat
 */
public class Water {

    /**
     * Run an instance of the water buckets puzzle.
     *
     * @param args [0]: desired amount of water to be collected;
     *             [1..N]: the capacities of the N available buckets.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(
                    ("Usage: java Water amount bucket1 bucket2 ...")
            );
        } else {
            ArrayList<Integer> startingConfig = new ArrayList<>();
            ArrayList<Integer> bucketSizes = new ArrayList<>();
            int target = Integer.parseInt(args[0]);
            for (int i = 1; i < args.length; i++) {
                bucketSizes.add(Integer.parseInt(args[i]));
            }
            for (int i = 0; i < bucketSizes.size(); i++) {
                startingConfig.add(0);
            }
            WaterConfig waterConfig = new WaterConfig(startingConfig, target, bucketSizes);
            Solver solver = new Solver();
            List<Configuration> path = solver.bfs(waterConfig);
            System.out.println("Amount: " + args[0] + ", Buckets: " + bucketSizes);
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
