package puzzles.clock;

import puzzles.common.solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Configuration that is a representation of the clock puzzle
 *
 * @author Chloe Nuzillat
 */
public class ClockConfig implements Configuration {
    private int current;
    private int target;
    private int hours;

    /**
     * Creates an instance of a clock configuration
     *
     * @param current the current hour
     * @param target the target hour
     * @param hours the number hours on the clock
     */
    public ClockConfig(int current, int target, int hours) {
        this.current = current;
        this.target = target;
        this.hours = hours;
    }

    /**
     * Checks whether a configuration is the solution
     *
     * @return if it's solution
     */
    @Override
    public boolean isSolution() {
        return current == target;
    }

    /**
     * Gets the neighbors of a configuration
     *
     * @return the neighbors
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        Collection<Configuration> neighbors = new ArrayList<Configuration>();
        int forward = (current % hours) + 1;
        int backward = (current - 2 + hours) % hours + 1;;
        neighbors.add(new ClockConfig(forward, target, hours));
        neighbors.add(new ClockConfig(backward, target, hours));
        return neighbors;
    }

    /**
     * Checks whether an object is equal to a clock configuration
     *
     * @return if it's equal
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ClockConfig)) {
            return false;
        }
        else {
            ClockConfig other = (ClockConfig) obj;
            return current == other.current && target == other.target && hours == other.hours;
        }
    }

    /**
     * Creates a hash code for a clock configuration
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(current, target, hours);
    }

    /**
     * Creates a string representation of the clock configuration
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return String.valueOf(current);
    }
}
