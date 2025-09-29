package puzzles.common.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * Solver for the clock and water puzzle
 *
 * @author Chloe Nuzillat
 */
public class Solver {
    private int totalConfigs = 0;
    private int uniqueConfigs = 0;

    /**
     * BFS to find the shortest path
     *
     * @param config the starting configuration
     * @return the shortest path
     */
    public List<Configuration> bfs(Configuration config) {
        LinkedList<Configuration> queue = new LinkedList<>();
        HashMap<Configuration, Configuration> predecessors = new HashMap<>();
        queue.add(config);
        predecessors.put(config, null);
        totalConfigs = 1;
        uniqueConfigs = 1;
        while (!queue.isEmpty()) {
            Configuration current = queue.removeFirst();
            if (current.isSolution()) {
                List<Configuration> path = new ArrayList<>();
                for (Configuration at = current; at != null; at = predecessors.get(at)) {
                    path.addFirst(at);
                }
                return path;
            }
            for (Configuration neighbor : current.getNeighbors()) {
                totalConfigs++;
                if (!predecessors.containsKey(neighbor)) {
                    predecessors.put(neighbor, current);
                    queue.add(neighbor);
                    uniqueConfigs++;
                }
            }
        }
        return null;
    }

    /**
     * Gets the total number of configurations generated
     *
     * @return the total number of configurations
     */
    public int getTotalConfigs() {
        return totalConfigs;
    }

    /**
     * Gets the number of unique configurations generated
     *
     * @return the number of unique configurations
     */
    public int getUniqueConfigs() {
        return uniqueConfigs;
    }
}
