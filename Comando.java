import java.io.*;
import java.math.BigInteger;

public class Comando implements Serializable{

    private static final long serialVersionUID = 1L;
    int cmd;
    BigInteger chave;
    String valor;

    Comando(int cmd, BigInteger chave, String valor){
        this.cmd = cmd;
        this.chave = chave;
        this.valor = valor;
    }
}