package puzzles.tilt.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import puzzles.common.Observer;
import puzzles.tilt.model.TiltModel;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

/**
 * GUI for Tilt
 *
 * @author Yori Messina
 */
public class TiltGUI extends Application implements Observer<TiltModel, String> {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    // for demonstration purposes
    private Image greenDisk = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"green.png"), 50, 50, false, false);
    private Image blueDisk = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"blue.png"), 50, 50, false, false);
    private Image block = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"block.png"), 50, 50, false, false);
    private Image hole = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"hole.png"), 50, 50, false, false);

    private TiltModel model;
    private String filename;
    private String msg;

    private GridPane grid;
    private Button[][] boardUI;

    public void init() {
        filename = getParameters().getRaw().get(0);
        model = new TiltModel();
        model.addObserver(this);
        msg = "";
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        // Top
        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER);
        Label message = new Label();
        message.setStyle("-fx-font-size: 15;");
        topBox.getChildren().add(message);
        root.setTop(topBox);

        // Right
        VBox rightBox = new VBox();
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setSpacing(20);

        Button load = new Button("Load");
        Button reset = new Button("Reset");
        Button hint = new Button("Hint");

        load.setStyle("-fx-font-size: 15;");
        reset.setStyle("-fx-font-size: 15;");
        hint.setStyle("-fx-font-size: 15;");

        load.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
            currentPath += File.separator + "data" + File.separator + "tilt";
            chooser.setInitialDirectory(new File(currentPath));

            File selectedFile = chooser.showOpenDialog(stage);
            if (selectedFile != null) {
                filename = selectedFile.getAbsolutePath();
                try {
                    model.load(filename);
                } catch (FileNotFoundException ex) {
                    message.setText("File not found: " + filename);
                }
            }
        });

        reset.setOnAction(e -> {
            try {
                model.reset();
                message.setText(msg);
            } catch (FileNotFoundException ex) {
                message.setText("No loaded file");
            }
        });

        hint.setOnAction(e -> {
            model.getHint();
            message.setText(msg);
        });


        rightBox.getChildren().addAll(load, reset, hint);
        root.setRight(rightBox);

        // The Rest
        BorderPane pane = new BorderPane();
        HBox nBox = new HBox();
        nBox.setAlignment(Pos.CENTER);
        HBox sBox = new HBox();
        sBox.setAlignment(Pos.CENTER);
        VBox eBox = new VBox();
        eBox.setAlignment(Pos.CENTER);
        VBox wBox = new VBox();
        wBox.setAlignment(Pos.CENTER);

        Button north = new Button("^");
        Button south = new Button("v");
        Button east = new Button(">");
        Button west = new Button("<");

        north.setOnAction(e -> {
            model.tilt("N");
            message.setText(msg);
        });
        south.setOnAction(e -> {
            model.tilt("S");
            message.setText(msg);
        });
        east.setOnAction(e -> {
            model.tilt("E");
            message.setText(msg);
        });
        west.setOnAction(e -> {
            model.tilt("W");
            message.setText(msg);
        });

        nBox.getChildren().add(north);
        sBox.getChildren().add(south);
        eBox.getChildren().add(east);
        wBox.getChildren().add(west);

        north.setStyle("-fx-font-size: 15px;");
        north.setMinSize(200, 40);
        south.setStyle("-fx-font-size: 15px;");
        south.setPrefSize(200, 40);
        east.setStyle("-fx-font-size: 15px;");
        east.setPrefSize(40, 250);
        west.setStyle("-fx-font-size: 15px;");
        west.setPrefSize(40, 250);

        pane.setTop(nBox);
        pane.setBottom(sBox);
        pane.setRight(eBox);
        pane.setLeft(wBox);

        grid = new GridPane();
        model.load(filename);
        generateBoard();
        pane.setCenter(grid);

        root.setCenter(pane);
        // Stage Setup
        stage.setScene(new Scene(root));
        stage.setTitle("Tilt");
        stage.show();
    }

    private void generateBoard(){
        grid.getChildren().clear();
        String[][] board = model.getCurrentConfig().getBoard();
        int row = board.length;
        int col = board[0].length;
        boardUI = new Button[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Button b = new Button();
                b.setPrefSize(60, 60);
                boardUI[i][j] = b;

                if (board[i][j].equals("O")){
                    b.setGraphic(new ImageView(hole));
                }
                else if (board[i][j].equals("*")){
                    b.setGraphic(new ImageView(block));
                }
                else if (board[i][j].equals("G")){
                    b.setGraphic(new ImageView(greenDisk));
                }
                else if (board[i][j].equals("B")){
                    b.setGraphic(new ImageView(blueDisk));
                }
                else{
                    b.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px;");
                }

                grid.add(b, j, i);
            }
        }
    }

    @Override
    public void update(TiltModel tiltModel, String message) {
        msg = message;
        generateBoard();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java TiltGUI filename");
            System.exit(0);
        } else {
            Application.launch(args);
        }
    }
}
