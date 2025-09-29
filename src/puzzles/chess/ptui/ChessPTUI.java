package puzzles.chess.ptui;

import puzzles.chess.model.ChessConfig;
import puzzles.chess.model.ChessModel;
import puzzles.common.Observer;
import puzzles.common.solver.Configuration;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The PTUI for the chess puzzle
 *
 * @author Chloe Nuzillat
 */
public class ChessPTUI implements Observer<ChessModel, String> {
    private ChessModel model;
    private String currentFile;

    /**
     * The constructor for the chess PTUI
     *
     * @param filename the file of the initial board
     */
    public ChessPTUI(String filename) throws FileNotFoundException {
        model = new ChessModel();
        model.addObserver(this);
        model.load(filename);
        System.out.println();
        currentFile = filename;
    }

    /**
     * Runs the program
     */
    private void run() throws FileNotFoundException {
        printChoices();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            System.out.print("> ");
            if (line.isEmpty()) continue;
            String[] commands = line.split("\\s+");
            switch (commands[0]) {
                case "h":
                case "hint":
                    model.hint();
                    break;
                case "l":
                case "load":
                    if (commands.length != 2) {
                        System.out.println("Failed to load: " + commands[1]);
                    }
                    else {
                        try {
                            model.load(commands[1]);
                        }
                        catch (FileNotFoundException e){
                            System.out.println("Failed to load: " + e.getMessage());
                        }
                    }
                    break;
                case "s":
                case "select":
                    if (commands.length == 3) {
                        try {
                            int row = Integer.parseInt(commands[1]);
                            int col = Integer.parseInt(commands[2]);
                            model.select(row, col);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid row/column values.");
                        }
                    } else {
                        System.out.println("Invalid selection (" + commands[1] + ", " + commands[2] + ")");
                    }
                    break;
                case "r":
                case "reset":
                    model.load(currentFile);
                    System.out.println();
                    model.reset();
                    break;
                case "q":
                case "quit":
                    model.quit();
                    break;
            }
        }
    }

    /**
     * Prints the possible choices that can be made
     */
    public void printChoices() {
        System.out.println("h(int)              -- hint next move");
        System.out.println("l(oad) filename     -- load new puzzle file");
        System.out.println("s(elect) r c        -- select cell at r, c");
        System.out.println("q(uit)              -- quit the game");
        System.out.println("r(eset)             -- reset the current game");
    }

    /**
     * Prints a visual representation of the chess board
     */
    public void printBoard() {
        ChessConfig config = model.getCurrentConfig();
        String[][] board = config.getBoard();
        int size = board.length;
        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print("--");
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.print(i + "| ");
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Updates the board
     *
     * @param model the model
     * @param message the message
     */
    @Override
    public void update(ChessModel model, String message) {
        if (message != null && !message.isEmpty()) {
            System.out.println(message);
        }
        printBoard();
    }

    /**
     * The main method
     *
     * @param args the arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Usage: java ChessPTUI filename");
        }
        ChessPTUI ptui = new ChessPTUI(args[0]);
        ptui.run();
    }
}
