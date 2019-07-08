package server.receptor;

import server.business.persistence.Manipulator;
import server.commons.chord.Chord;
import server.commons.chord.ChordUtils;

import java.util.Arrays;
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
                    System.out.println(Chord.getChodNode().getKey());
                    break;

                case "ft":
                    Chord.getFt()
                            .getMap()
                            .forEach(
                                    (pos, value) -> {
                                        int suc = ChordUtils.successor(Chord.getChodNode().getKey(), pos);
                                        if(suc > Chord.getFt().getRange())
                                            suc -= Chord.getFt().getRange();

                                        System.out.println("Suc: " + suc + " pos: " + pos + " -> " + value.getRange());
                                    }
                            );
                    break;

                case "range":
                    System.out.println(Arrays.toString(Chord.getChodNode().getRange().toArray()));
                    break;

                case "bd":
                    Manipulator.getDb().forEach(
                            (pos, value) ->  System.out.println("Key: " + pos + " ->  " + Arrays.toString(value))
                    );

                default:
                    break;
            }
        }
    }
}
