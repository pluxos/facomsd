package application;

public enum Menu {
    CREATE("1"),
    READ("2"),
    UPDATE("3"),
    DELETE("4"),
    EXIT("5");



    private final String code;

    Menu(String item) {
        this.code = item;
    }

    public String toString() {
        return code;
    }
}
