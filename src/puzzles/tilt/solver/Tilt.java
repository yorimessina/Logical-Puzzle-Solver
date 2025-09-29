package puzzles.tilt.solver;

import java.util.*;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.tilt.model.TiltConfig;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Solver for Tilt Puzzle
 *
 * @author Yori Messina
 */
public class Tilt {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Usage: java Tilt filename");
        }
        else {
            String filename = args[0];
            System.out.println("File: '" + filename);

            try (BufferedReader br = new BufferedReader(new FileReader(filename))){
                String line = br.readLine();

                int dim = Integer.parseInt(line.strip());
                String[][] board = new String[dim][dim];

                for (int i = 0; i < dim; i++) {
                    line = br.readLine();
                    System.out.println(line);
                    String[] row = line.split(" ");
                    board[i] = row;
                }


                Solver solver = new Solver();
                TiltConfig config = new TiltConfig(dim, dim, board);
                List<Configuration> solution = solver.bfs(config);

                System.out.println("Total configs: " + solver.getTotalConfigs());
                System.out.println("Unique configs: " + solver.getUniqueConfigs());

                if (solution != null) {
                    int step = 0;
                    for (Configuration x : solution) {
                        System.out.println();
                        System.out.println("Step " + step + ":");
                        System.out.print(x);
                        step++;
                    }
                }
                else{
                    System.out.println("No solution");
                }
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }
}
