package server.receptor;

import server.commons.chord.Chord;
import server.commons.chord.ChordUtils;

import java.util.Scanner;

public class UtilsCommands implements Runnable {

    private Scanner input;

    UtilsCommands() {
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
                    Chord.getFt()
                            .getMap()
                            .forEach(
                                    (pos, value) -> {
                                        int suc = ChordUtils.successor(Chord.getFt().getKey(), pos);
                                        if(suc > Chord.getFt().getRange())
                                            suc -= Chord.getFt().getRange();

                                        System.out.println("Suc: " + suc + " pos: " + pos + " -> " + value.getRange());
                                    }
                            );
                    break;

                case "range":
                    System.out.println(Chord.getNode().getRange());
                    break;

                default:
                    break;
            }
        }
    }
}
