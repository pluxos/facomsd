package client;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main  {

    public static void main( String[] args ) {
        Operation operation;

        Scanner scanner = new Scanner( System.in );

        int menuOption = 4;
        BigInteger key;
        String value = null;

        while ( true ) {
            menuOption = showMenu( menuOption, scanner );

            System.out.println("--------------------");
            System.out.println("Key: ");
            key = scanner.nextBigInteger();

            if ( menuOption == MenuOptions.CREATE.getValue() || menuOption == MenuOptions.UPDATE.getValue() ) {
                System.out.println("Value: ");
                value = scanner.next();
            }

            Thread t = new ThreadProcess( key, value, menuOption );
            t.start();
        }
    }

    private static int showMenu( int option, Scanner scanner ) {
        do {
            MenuOptions[] menu = MenuOptions.values();

            for ( MenuOptions menus : menu ) System.out.printf( "[%d] - %s%n", menus.ordinal(), menus.name() );

            System.out.println( "Option: " );
            option = scanner.nextInt();
        } while ( option > 3 || option < 0 );

        return option;
    }
}
