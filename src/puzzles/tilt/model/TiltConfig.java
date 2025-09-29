package puzzles.tilt.model;

// TODO: implement your TiltConfig for the puzzles.common solver

import puzzles.common.solver.Configuration;

import java.util.Collection;
import java.util.*;

/**
 * Representation for the board of Tilt
 *
 * @author Yori Messina
 */
public class TiltConfig implements Configuration {
    private int row;
    private int col;
    private String[][] board;
    private static int numBlue;

    public TiltConfig(int row, int col, String[][] board) {
        this.row = row;
        this.col = col;
        this.board = board;
        numBlue = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j].equals("B")){
                    numBlue++;
                }
            }
        }
    }

    public TiltConfig(int row, int col, String[][] board, int numBlue) {
        this.row = row;
        this.col = col;
        this.board = board;
    }

    public String[][] copyBoard(){
        String[][] newBoard = new String[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                newBoard[i][j] = this.board[i][j];
            }
        }
        return newBoard;
    }

    public String[][] getBoard(){
        return board;
    }

    public TiltConfig tilt(String dir){
        String[][] newBoard = copyBoard();

        if (dir.equals("E")){
            for (int i = 0; i < row ; i++) {
                for (int j = 0; j < col; j++) {
                    if (newBoard[i][j].equals("G") || newBoard[i][j].equals("B")) {
                        newBoard = slideEast(newBoard, i, j);
                    }
                }
            }
        }

        else if (dir.equals("W")){
            for (int i = 0; i < row ; i++) {
                for (int j = col - 1; j > 0; j--) {
                    if (newBoard[i][j].equals("G") || newBoard[i][j].equals("B")) {
                        newBoard = slideWest(newBoard, i, j);
                    }
                }
            }
        }

        else if (dir.equals("S")){
            for (int j = 0; j < col ; j++) {
                for (int i = 0; i < row; i++) {
                    if (newBoard[i][j].equals("G") || newBoard[i][j].equals("B")) {
                        newBoard = slideSouth(newBoard, i, j);
                    }
                }
            }
        }

        else if (dir.equals("N")){
            for (int j = 0; j < col ; j++) {
                for (int i = row - 1; i > 0; i--) {
                    if (newBoard[i][j].equals("G") || newBoard[i][j].equals("B")) {
                        newBoard = slideNorth(newBoard, i, j);
                    }
                }
            }
        }
        else{
            return null;
        }

        if (isValidBoard(newBoard)){
            return new TiltConfig(row, col, newBoard, numBlue);
        }
        else {
            return null;
        }
    }

    @Override
    public boolean isSolution() {
        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < col ; j++) {
                if (board[i][j].equals("G")) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        Set<Configuration> neighbors = new LinkedHashSet<>();
        String[][] newBoard = copyBoard();

        // Tilt East
        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < col; j++) {
                if (newBoard[i][j].equals("G") || newBoard[i][j].equals("B")) {
                    newBoard = slideEast(newBoard, i, j);
                }
            }
        }
        if (isValidBoard(newBoard)) {
            neighbors.add(new TiltConfig(row, col, newBoard, numBlue));
        }
        newBoard = copyBoard();

        // Tilt West
        for (int i = 0; i < row ; i++) {
            for (int j = col - 1; j > 0; j--) {
                if (newBoard[i][j].equals("G") || newBoard[i][j].equals("B")) {
                    newBoard = slideWest(newBoard, i, j);
                }
            }
        }
        if (isValidBoard(newBoard)) {
            neighbors.add(new TiltConfig(row, col, newBoard, numBlue));
        }
        newBoard = copyBoard();

        // Tilt South
        for (int j = 0; j < col ; j++) {
            for (int i = 0; i < row; i++) {
                if (newBoard[i][j].equals("G") || newBoard[i][j].equals("B")) {
                    newBoard = slideSouth(newBoard, i, j);
                }
            }
        }
        if (isValidBoard(newBoard)) {
            neighbors.add(new TiltConfig(row, col, newBoard, numBlue));
        }
        newBoard = copyBoard();

        // Tilt North
        for (int j = 0; j < col ; j++) {
            for (int i = row - 1; i > 0; i--) {
                if (newBoard[i][j].equals("G") || newBoard[i][j].equals("B")) {
                    newBoard = slideNorth(newBoard, i, j);
                }
            }
        }
        if (isValidBoard(newBoard)) {
            neighbors.add(new TiltConfig(row, col, newBoard, numBlue));
        }

        return neighbors;
    }

    private String[][] slideEast(String[][] newBoard, int r, int c){
        if (c >= col - 1){
            return newBoard;
        }

        if (newBoard[r][c + 1].equals("B") || newBoard[r][c + 1].equals("G")){
            newBoard = slideEast(newBoard, r, c + 1);
        }

        if (newBoard[r][c + 1].equals(".")){
            newBoard[r][c + 1] = newBoard[r][c];
            newBoard[r][c] = ".";
        }
        else if (newBoard[r][c + 1].equals("O")){
            newBoard[r][c] = ".";
        }

        return newBoard;
    }

    private String[][]slideWest(String[][] newBoard, int r, int c){
        if (c < 1){
            return newBoard;
        }

        if (newBoard[r][c - 1].equals("B") || newBoard[r][c - 1].equals("G")){
            newBoard = slideWest(newBoard, r, c - 1);
        }

        if (newBoard[r][c - 1].equals(".")){
            newBoard[r][c - 1] = newBoard[r][c];
            newBoard[r][c] = ".";
        }
        else if (newBoard[r][c - 1].equals("O")){
            newBoard[r][c] = ".";
        }

        return newBoard;
    }

    private String[][]slideNorth(String[][] newBoard, int r, int c){
        if (r < 1){
            return newBoard;
        }

        if (newBoard[r - 1][c].equals("B") || newBoard[r - 1][c].equals("G")){
            newBoard = slideNorth(newBoard, r - 1, c);
        }

        if (newBoard[r - 1][c].equals(".")){
            newBoard[r - 1][c] = newBoard[r][c];
            newBoard[r][c] = ".";
        }
        else if (newBoard[r - 1][c].equals("O")){
            newBoard[r][c] = ".";
        }

        return newBoard;
    }

    private String[][]slideSouth(String[][] newBoard, int r, int c){
        if (r >= row - 1){
            return newBoard;
        }

        if (newBoard[r + 1][c].equals("B") || newBoard[r + 1][c].equals("G")){
            newBoard = slideSouth(newBoard, r + 1, c);
        }

        if (newBoard[r + 1][c].equals(".")){
            newBoard[r + 1][c] = newBoard[r][c];
            newBoard[r][c] = ".";
        }
        else if (newBoard[r + 1][c].equals("O")){
            newBoard[r][c] = ".";
        }

        return newBoard;
    }

    private boolean isValidBoard(String[][] newBoard){
        int count = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (newBoard[i][j].equals("B")){
                    count++;
                }
            }
        }

        return count == numBlue;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof TiltConfig) {
            TiltConfig o = (TiltConfig) other;

            return Arrays.deepEquals(this.board, o.board);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.board);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < col ; j++) {
                result.append(board[i][j]).append(" ");
            }
            result.append("\n");
        }

        return result.toString();
    }
}
