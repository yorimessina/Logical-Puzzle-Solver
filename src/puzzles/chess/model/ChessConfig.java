package puzzles.chess.model;

import puzzles.common.solver.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The representation of the chess board
 *
 * @author Chloe Nuzillat
 */
public class ChessConfig implements Configuration {
    private int rows;
    private int columns;
    private String[][] board;

    /**
     * Constructs the chess board
     *
     * @param rows the number of rows
     * @param columns the number or columns
     * @param board the board
     */
    public ChessConfig(int rows, int columns, String[][] board) {
        this.rows = rows;
        this.columns = columns;
        this.board = board;
    }

    /**
     * Constructs the chess board initially
     *
     * @param filename the file with a chess board
     */
    public ChessConfig(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        scanner.nextLine();
        String[][] board = new String[rows][columns];
        for (int r = 0; r < rows; r++) {
            String line = scanner.nextLine();
            String[] tokens = line.split(" ");
            System.arraycopy(tokens, 0, board[r], 0, columns);
        }
        scanner.close();
        this.rows = rows;
        this.columns = columns;
        this.board = board;
    }

    /**
     * Does the chess board have one piece left?
     *
     * @return solution or not?
     */
    @Override
    public boolean isSolution() {
        int pieces = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (!this.board[r][c].equals(".")) {
                    pieces++;
                }
            }
        }
        return pieces == 1;
    }

    /**
     * Creates a deep copy of a chess board
     *
     * @return the chess board
     */
    public String[][] copyBoard() {
        String[][] copy = new String[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                copy[r][c] = this.board[r][c];
            }
        }
        return copy;
    }

    /**
     * Gets all possible neighbors of a board
     *
     * @return the neighbors
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        Set<Configuration> neighbors = new LinkedHashSet<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (board[r][c].equals("P")) {
                    if (r - 1 >= 0 && c - 1 >= 0 && !board[r - 1][c - 1].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r - 1][c - 1] = "P";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    if (r - 1 >= 0 && c + 1 < columns && !board[r - 1][c + 1].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r - 1][c + 1] = "P";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                }
                if (board[r][c].equals("K")) {
                    if (r - 1 >= 0 && c - 1 >= 0 && !board[r - 1][c - 1].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r - 1][c - 1] = "K";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    if (r - 1 >= 0 && c + 1 < columns && !board[r - 1][c + 1].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r - 1][c + 1] = "K";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    if (r - 1 >= 0 && !board[r - 1][c].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r - 1][c] = "K";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    if (c + 1 < columns && !board[r][c + 1].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r][c + 1] = "K";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    if (r + 1 < rows && c + 1 < columns && !board[r + 1][c + 1].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r + 1][c + 1] = "K";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    if (r + 1 < rows && !board[r + 1][c].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r + 1][c] = "K";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    if (r + 1 < rows && c - 1 >= 0 && !board[r + 1][c - 1].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r + 1][c - 1] = "K";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    if (c - 1 >= 0 && !board[r][c - 1].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r][c - 1] = "K";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                }
                if (board[r][c].equals("R")) {
                    int tempRow = r - 1;
                    while (tempRow >= 0 && board[tempRow][c].equals(".")) {
                        tempRow--;
                    }
                    if (tempRow >= 0 && !board[tempRow][c].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[tempRow][c] = "R";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    int tempCol = c + 1;
                    while (tempCol < columns && board[r][tempCol].equals(".")) {
                        tempCol++;
                    }
                    if (tempCol < columns && !board[r][tempCol].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r][tempCol] = "R";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    tempRow = r + 1;
                    while (tempRow < rows && board[tempRow][c].equals(".")) {
                        tempRow++;
                    }
                    if (tempRow < rows && !board[tempRow][c].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[tempRow][c] = "R";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    tempCol = c - 1;
                    while (tempCol >= 0 && board[r][tempCol].equals(".")) {
                        tempCol--;
                    }
                    if (tempCol >= 0 && !board[r][tempCol].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r][tempCol] = "R";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                }
                if (board[r][c].equals("B")) {
                    int tempRow = r - 1;
                    int tempCol = c + 1;
                    while (tempRow >= 0 && tempCol < columns && board[tempRow][tempCol].equals(".")) {
                        tempRow--;
                        tempCol++;
                    }
                    if (tempRow >= 0 && tempCol < columns && !board[tempRow][tempCol].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[tempRow][tempCol] = "B";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    tempCol = c + 1;
                    tempRow = r + 1;
                    while (tempRow < rows && tempCol < columns && board[tempRow][tempCol].equals(".")) {
                        tempCol++;
                        tempRow++;
                    }
                    if (tempRow < rows && tempCol < columns && !board[tempRow][tempCol].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[tempRow][tempCol] = "B";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    tempRow = r + 1;
                    tempCol = c - 1;
                    while (tempRow < rows && tempCol >= 0 && board[tempRow][tempCol].equals(".")) {
                        tempRow++;
                        tempCol--;
                    }
                    if (tempRow < rows && tempCol >= 0 && !board[tempRow][tempCol].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[tempRow][tempCol] = "B";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    tempCol = c - 1;
                    tempRow = r - 1;
                    while (tempRow >= 0 && tempCol >= 0 && board[tempRow][tempCol].equals(".")) {
                        tempCol--;
                        tempRow--;
                    }
                    if (tempRow >= 0 && tempCol >= 0 && !board[tempRow][tempCol].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[tempRow][tempCol] = "B";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                }
                if (board[r][c].equals("Q")) {
                    int tempRow = r - 1;
                    int tempCol = c + 1;
                    while (tempRow >= 0 && tempCol < columns && board[tempRow][tempCol].equals(".")) {
                        tempRow--;
                        tempCol++;
                    }
                    if (tempRow >= 0 && tempCol < columns && !board[tempRow][tempCol].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[tempRow][tempCol] = "Q";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    tempCol = c + 1;
                    tempRow = r + 1;
                    while (tempCol < columns && tempRow < rows && board[tempRow][tempCol].equals(".")) {
                        tempCol++;
                        tempRow++;
                    }
                    if (tempCol < columns && tempRow < rows && !board[tempRow][tempCol].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[tempRow][tempCol] = "Q";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    tempRow = r + 1;
                    tempCol = c - 1;
                    while (tempRow < rows && tempCol >= 0 && board[tempRow][tempCol].equals(".")) {
                        tempRow++;
                        tempCol--;
                    }
                    if (tempRow < rows && tempCol >= 0 && !board[tempRow][tempCol].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[tempRow][tempCol] = "Q";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    tempCol = c - 1;
                    tempRow = r - 1;
                    while (tempRow >= 0 && tempCol >= 0 && board[tempRow][tempCol].equals(".")) {
                        tempCol--;
                        tempRow--;
                    }
                    if (tempRow >= 0 && tempCol >= 0 && !board[tempRow][tempCol].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[tempRow][tempCol] = "Q";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    tempRow = r - 1;
                    while (tempRow >= 0 && board[tempRow][c].equals(".")) {
                        tempRow--;
                    }
                    if (tempRow >= 0 && !board[tempRow][c].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[tempRow][c] = "Q";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    tempCol = c + 1;
                    while (tempCol < columns && board[r][tempCol].equals(".")) {
                        tempCol++;
                    }
                    if (tempCol < columns && !board[r][tempCol].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r][tempCol] = "Q";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    tempRow = r + 1;
                    while (tempRow < rows && board[tempRow][c].equals(".")) {
                        tempRow++;
                    }
                    if (tempRow < rows && !board[tempRow][c].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[tempRow][c] = "Q";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    tempCol = c - 1;
                    while (tempCol >= 0 && board[r][tempCol].equals(".")) {
                        tempCol--;
                    }
                    if (tempCol >= 0 && !board[r][tempCol].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r][tempCol] = "Q";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                }
                if (board[r][c].equals("N")) {
                    if (r + 2 < rows && c - 1 >= 0 && !board[r + 2][c - 1].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r + 2][c - 1] = "N";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    if (r + 2 < rows && c + 1 < columns && !board[r + 2][c + 1].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r + 2][c + 1] = "N";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    if (r + 1 < rows && c + 2 < columns && !board[r + 1][c + 2].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r + 1][c + 2] = "N";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    if (r - 1 >= 0 && c + 2 < columns && !board[r - 1][c + 2].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r - 1][c + 2] = "N";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    if (r - 2 >= 0 && c + 1 < columns && !board[r - 2][c + 1].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r - 2][c + 1] = "N";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    if (r - 2 >= 0 && c - 1 >= 0 && !board[r - 2][c - 1].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r - 2][c - 1] = "N";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    if (r + 1 < rows && c - 2 >= 0 && !board[r + 1][c - 2].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r + 1][c - 2] = "N";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                    if (r - 1 >= 0 && c - 2 >= 0 && !board[r - 1][c - 2].equals(".")) {
                        String[][] copyBoard = copyBoard();
                        copyBoard[r][c] = ".";
                        copyBoard[r - 1][c - 2] = "N";
                        neighbors.add(new ChessConfig(rows, columns, copyBoard));
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Is a chess board equal to another?
     *
     * @return is it equal or not?
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChessConfig that)) {
            return false;
        }
        return Arrays.deepEquals(this.board, that.board);
    }

    /**
     * A hashcode for the board
     *
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.board);
    }

    /**
     * Creates a string representation for the board
     *
     * @return the string
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0 ; i < rows ; i++) {
            for (int j = 0 ; j < columns ; j++) {
                result.append(board[i][j]).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Gets the chess board
     *
     * @return the board
     */
    public String[][] getBoard() {
        return this.board;
    }

    /**
     * Is there a piece at this point?
     *
     * @boolean if there's a piece or not
     */
    public boolean hasPieceAt(int row, int col) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return false;
        }
        return !board[row][col].equals(".");
    }

    /**
     * Makes a valid move when selected or keeps the board the same if the move isn't valid
     *
     * @return the new board
     */
    public ChessConfig makeMove(int startRow, int startCol, int endRow, int endCol) {
        String[][] copyBoard = copyBoard();
        String piece = copyBoard[startRow][startCol];
        String destination = copyBoard[endRow][endCol];
        Collection<Configuration> neighbors = getNeighbors();
        if (!piece.equals(".") && !destination.equals(".")) {
            if (piece.equals("R") || piece.equals("B") || piece.equals("Q")) {
                if (!isPathClear(copyBoard, startRow, startCol, endRow, endCol)) {
                    return null;
                }
            }
            copyBoard[startRow][startCol] = ".";
            copyBoard[endRow][endCol] = piece;
            ChessConfig config = new ChessConfig(rows, columns, copyBoard);
            for (Configuration neighbor : neighbors) {
                if (neighbor.equals(config)) {
                    return config;
                }
            }
        }
        return null;
    }

    /**
     * Checks if the path is clear for certain pieces
     *
     * @return is the path clear?
     */
    private boolean isPathClear(String[][] board, int fromRow, int fromCol, int toRow, int toCol) {
        int rowStep = Integer.compare(toRow, fromRow);
        int colStep = Integer.compare(toCol, fromCol);
        int currentRow = fromRow + rowStep;
        int currentCol = fromCol + colStep;
        while (currentRow != toRow || currentCol != toCol) {
            if (!board[currentRow][currentCol].equals(".")) {
                return false;
            }
            currentRow += rowStep;
            currentCol += colStep;
        }
        return true;
    }
}
