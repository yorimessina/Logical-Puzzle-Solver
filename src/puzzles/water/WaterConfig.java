package puzzles.water;

import puzzles.common.solver.Configuration;

import java.util.*;

/**
 * Configuration that is a representation of the water puzzle
 *
 * @author Chloe Nuzillat
 */
public class WaterConfig implements Configuration {
    private ArrayList<Integer> buckets;
    private int target;
    private ArrayList<Integer> bucketsSizes;

    /**
     * Creates an instance of a clock configuration
     *
     * @param buckets the current bucket values
     * @param target the target bucket amount
     * @param bucketsSizes the amount each bucket can hold
     */
    public WaterConfig(ArrayList<Integer> buckets, int target, ArrayList<Integer> bucketsSizes) {
        this.buckets = buckets;
        this.target = target;
        this.bucketsSizes = bucketsSizes;
    }

    /**
     * Checks whether a configuration is the solution
     *
     * @return if it's solution
     */
    @Override
    public boolean isSolution() {
        for (Integer bucket : buckets) {
            if (bucket == target) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a copy of the current bucket values
     *
     * @return copy buckets values
     */
    private ArrayList<Integer> copyBuckets() {
        return new ArrayList<>(buckets);
    }

    /**
     * Gets the neighbors of a configuration
     *
     * @return the neighbors
     */
    @Override
    public Set<Configuration> getNeighbors() {
        Set<Configuration> neighbors = new LinkedHashSet<>();
        for (int i = 0; i < buckets.size(); i++) {
            ArrayList<Integer> filled = copyBuckets();
            filled.set(i, bucketsSizes.get(i));
            neighbors.add(new WaterConfig(filled, target, bucketsSizes));
            int current = buckets.get(i);
            ArrayList<Integer> emptied = copyBuckets();
            emptied.set(i, 0);
            neighbors.add(new WaterConfig(emptied, target, bucketsSizes));
            for (int j = 0; j < buckets.size(); j++) {
                if (i == j) continue;
                ArrayList<Integer> poured = copyBuckets();
                int from = poured.get(i);
                int to = poured.get(j);
                int space = bucketsSizes.get(j) - to;
                int amountToPour = Math.min(from, space);
                poured.set(i, from - amountToPour);
                poured.set(j, to + amountToPour);
                neighbors.add(new WaterConfig(poured, target, bucketsSizes));
            }
        }
        return neighbors;
    }

    /**
     * Checks whether an object is equal to a clock configuration
     *
     * @return if it's equal
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WaterConfig other)) {
            return false;
        }
        else {
            return buckets.equals(other.buckets) && target == other.target && bucketsSizes.equals(other.bucketsSizes);
        }
    }

    /**
     * Creates a hash code for a water configuration
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(buckets);
    }

    /**
     * Creates a string representation of the clock configuration
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return buckets.toString();
    }
}
