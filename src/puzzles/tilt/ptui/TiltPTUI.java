package puzzles.tilt.ptui;

import puzzles.common.Observer;
import puzzles.tilt.model.TiltModel;
import puzzles.tilt.solver.Tilt;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * PTUI for Tilt
 *
 * @author Yori Messina
 */
public class TiltPTUI implements Observer<TiltModel, String> {
    private TiltModel model;

    public TiltPTUI(String filename) throws FileNotFoundException {
        model = new TiltModel();

        model.addObserver(this);
        model.load(filename);
        System.out.println();
    }

    public void run() throws FileNotFoundException {
        helpMessage();

        while(true){
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();

            if (input.isEmpty()){
                continue;
            }
            String[] com = input.split(" ");

            System.out.print("> ");

            switch(com[0].toLowerCase()){
                case "h":
                case "hint":
                    model.getHint();
                    break;

                case "l":
                case "load":
                    if (com.length < 2){
                        System.out.println("Please enter a filename");
                    }
                    else{
                        try{
                            model.load(com[1]);
                        } catch (Exception e) {
                            System.out.println("Failed to load: " + e.getMessage());
                        }
                    }
                    break;

                case "t":
                case "tilt":
                    String dir = com[1].toUpperCase();

                    if (com.length < 2){
                        System.out.println("Please enter a direction");
                    }
                    else if (!(dir.equals("N") || dir.equals("S") || dir.equals("E") || dir.equals("W"))){
                        System.out.println("Please enter a valid direction");
                    }
                    else{
                        model.tilt(dir);
                    }
                    break;

                case "q":
                case "quit":
                    model.quit();
                    break;

                case "r":
                case "reset":
                    model.reset();
                    break;

                default:
                    System.out.println("Invalid command");
            }
        }
    }

    private void helpMessage(){
        System.out.println("h(int)              -- hint next move");
        System.out.println("l(oad) filename     -- load new puzzle file");
        System.out.println("t(ilt) {N|S|E|W}    -- tilt the board in the given direction");
        System.out.println("q(uit)              -- quit the game");
        System.out.println("r(eset)             -- reset the current game");
    }

    @Override
    public void update(TiltModel model, String message) {
        System.out.println(message);
        System.out.println(model.getCurrentConfig().toString());
    }

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Usage: java TiltPTUI filename");
        }
        else{
            TiltPTUI ui = new TiltPTUI(args[0]);
            ui.run();
        }
    }
}
