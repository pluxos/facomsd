package model;

public enum Menu {

    CREATE(0),
    READ(1),
    UPDATE(2),
    DELETE(3),
    REGISTER(4);

    private final int valor;

    Menu(int valorOpcao){
        valor = valorOpcao;
    }
    public int getValor(){
        return valor;
    }

}