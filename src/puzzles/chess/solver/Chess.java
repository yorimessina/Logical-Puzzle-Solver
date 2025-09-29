package puzzles.chess.solver;

import puzzles.chess.model.ChessConfig;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * The main class for chess
 *
 * @author Chloe Nuzillat
 */
public class Chess {

    /**
     * The main method
     *
     * @param args the arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Usage: java Chess filename");
        } else {
            String filename = args[0];
            System.out.println("File: " + filename);
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String firstLine = br.readLine();
                String[] dimensions = firstLine.split(" ");
                int rows = Integer.parseInt(dimensions[0]);
                int cols = Integer.parseInt(dimensions[1]);
                String[][] board = new String[rows][cols];
                for (int i = 0; i < rows; i++) {
                    String line = br.readLine();
                    String[] parts = line.split(" ");
                    for (int j = 0; j < cols; j++) {
                        board[i][j] = parts[j];
                    }
                }
                for (int i = 0; i < rows; i++) {
                    System.out.println(String.join(" ", board[i]));
                }
                Solver solver = new Solver();
                ChessConfig chessConfig = new ChessConfig(rows, cols, board);
                List<Configuration> solution = solver.bfs(chessConfig);
                System.out.println("Total configs: " + solver.getTotalConfigs());
                System.out.println("Unique configs: " + solver.getUniqueConfigs());
                if (!(solution == null)) {
                    int step = 0;
                    for (Configuration config : solution) {
                        System.out.println();
                        System.out.println("Step " + step + ":");
                        System.out.print(config);
                        step++;
                    }
                }
                else{
                    System.out.println("No solution");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
