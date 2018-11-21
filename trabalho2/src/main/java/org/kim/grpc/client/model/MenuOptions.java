package org.kim.grpc.client.model;

public enum MenuOptions {

    CREATE   (0),
    READ     (1),
    UPDATE   (2),
    DELETE   (3),
    REGISTER (4);

    private final int value;

    MenuOptions(int valueOption){ value = valueOption; }

    public int getValue(){ return value; }
}
