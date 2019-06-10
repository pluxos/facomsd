package server.receptor;

import server.commons.chord.Chord;

import java.util.Scanner;

public class UtilsCommands implements Runnable {

    private Scanner input;

    public UtilsCommands() {
        this.input = new Scanner(System.in);
    }

    @Override
    public void run() {
        for(;;){
            String command = this.input.nextLine();

            switch (command){
                case "key":
                    System.out.println(Chord.getFt().getKey());
                    break;
                case "ft":
                    Chord.getFt().getMap().forEach((key, value) -> System.out.println("pos: " + key + " -> " + value.getRange()));
                    break;
                default:
                    System.err.println(Chord.getNode().getRange());
                    break;
            }
        }
    }
}
