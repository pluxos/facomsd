package client;

public enum MenuOptions {

    CREATE( 0 ),
    READ  ( 1 ),
    UPDATE( 2 ),
    DELETE( 3 );

    private final int value;

    MenuOptions( int optionValue ) {
        value = optionValue;
    }

    public int getValue() {
        return value;
    }
}
