package puzzles.chess.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import puzzles.chess.model.ChessModel;
import puzzles.common.Observer;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * GUI for the chess puzzle
 *
 * @author Chloe Nuzillat
 */
public class ChessGUI extends Application implements Observer<ChessModel, String> {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";
    private int rows;
    private int cols;
    private ChessModel model;
    private Label messageLabel = new Label();
    private String filename;
    private GridPane gridPane;
    private Button[][] boardButtons;
    private Image pawn = new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR +
            "pawn.png")));
    private Image king = new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR +
            "king.png")));
    private Image knight = new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR +
            "knight.png")));
    private Image queen = new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR +
            "queen.png")));
    private Image rook = new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR +
            "rook.png")));
    private Image bishop = new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR +
            "bishop.png")));

    /**
     * Initializes the GUI
     */
    public void init() throws FileNotFoundException {
        filename = getParameters().getRaw().getFirst();
        model = new ChessModel();
        model.addObserver(this);
    }

    /**
     * Sets up the necessary components of the GUI
     * @param stage the stage of the GUI
     */
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        HBox topHBox = new HBox();
        topHBox.setAlignment(Pos.CENTER);
        topHBox.getChildren().add(messageLabel);
        borderPane.setTop(topHBox);
        HBox bottomHBox = new HBox();
        Button loadButton = new Button("Load");
        loadButton.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
            currentPath += File.separator + "data" + File.separator + "chess";
            chooser.setInitialDirectory(new File(currentPath));
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files",
                    "*.txt"));
            File selectedFile = chooser.showOpenDialog(stage);
            String cwd = System.getProperty("user.dir");
            if (selectedFile != null) {
                filename = selectedFile.getAbsolutePath().substring(cwd.length() + 1);
                try {
                    model.load(filename);
                    buildBoard();
                    stage.setWidth(rows * 100 + 10);
                    stage.setHeight(cols * 100 + 100);
                } catch (FileNotFoundException e) {
                    messageLabel.setText("File not found: " + filename);
                }
            }
        });
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event -> model.reset());
        Button hintButton = new Button("Hint");
        hintButton.setOnAction(event -> model.hint());
        bottomHBox.getChildren().addAll(loadButton, resetButton, hintButton);
        bottomHBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(bottomHBox);
        gridPane = new GridPane();
        borderPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        model.load(filename);
        buildBoard();
        Scene scene = new Scene(borderPane);
        stage.setTitle("Chess GUI");
        stage.setScene(scene);
        stage.setWidth(rows * 100 + 10);
        stage.setHeight(cols * 100 + 100);
        stage.show();
    }

    /**
     * Helper function that builds the board
     */
    public void buildBoard() {
        gridPane.getChildren().clear();
        String[][] board = model.getBoard();
        rows = board.length;
        cols = board[0].length;
        boardButtons = new Button[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String piece = board[i][j];
                Button button = new Button();
                button.setMinSize(100, 100);
                boardButtons[i][j] = button;
                int finalI = i;
                int finalJ = j;
                button.setOnAction(event -> model.select(finalI, finalJ));
                setPieceGraphic(button, piece);
                if ((i + j) % 2 == 0) {
                    button.setStyle("-fx-background-color: white;");
                } else {
                    button.setStyle("-fx-background-color: darkblue;");
                }
                gridPane.add(button, j, i);
            }
        }
    }

    /**
     * Helper function that assigns the buttons the correct graphics
     * @param button the button
     * @param piece the piece being assigned
     */
    public void setPieceGraphic(Button button, String piece) {
        switch (piece) {
            case "P" -> button.setGraphic(new ImageView(pawn));
            case "K" -> button.setGraphic(new ImageView(king));
            case "Q" -> button.setGraphic(new ImageView(queen));
            case "B" -> button.setGraphic(new ImageView(bishop));
            case "N" -> button.setGraphic(new ImageView(knight));
            case "R" -> button.setGraphic(new ImageView(rook));
            default -> button.setGraphic(null);
        }
    }

    /**
     * Updates the view when the model is changed
     * @param chessModel the model being changed
     * @param message the message being displayed
     */
    @Override
    public void update(ChessModel chessModel, String message) {
        messageLabel.setText(message);
        buildBoard();
    }

    /**
     * The main method
     * @param args file name
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ChessGUI filename");
            System.exit(0);
        } else {
            Application.launch(args);
        }
    }
}
