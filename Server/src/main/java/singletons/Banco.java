package singletons;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import java.lang.*;

public class Banco {
    private int x = 1;
    public Map<BigInteger, byte[]> database = new HashMap<BigInteger, byte[]>();
    boolean free = true;

    private static Banco ourInstance = new Banco();

    public static Banco getInstance() {
        return ourInstance;
    }

    public void blockDatabase() {
        free = false;
    }

    public void counterIncrement() {
        x++;
    }

    public void freeDatabase() {
        free = true;
    }

    public boolean isFree() {
        return free;
    }

    private Banco() {
    }

    public int getNumber() {
        return x;
    }

    @Override
    public String toString() {
        String str = new String();
        for (Map.Entry<BigInteger, byte[]> entry : database.entrySet())
            str += entry.getKey() + " " + new String(entry.getValue()) + "\n";
        return str;
    }

    public Boolean Insert(BigInteger key, byte[] value) {
        System.out.println("Insert <" + key + "," + new String(value) + ">");
        if (this.database.containsKey(key)) {
            return false; // Se a chave já existir no banco, ele retorna falso
        } else {
            this.toString();
            this.database.put(key, value); // Se não existir, ele insere a chave e retorna verdadeiro
            return true;
        }
    }

    public Boolean Insert(String par) {
        // Insert from a String
        String[] words = new String[2];
        words = par.split(" ");
        return Insert(new BigInteger(words[0]), words[1].getBytes());
    }

    public byte[] Read(BigInteger key) {
        System.out.println("Read <" + key + ">");
        if (this.database.containsKey(key)) {
            return this.database.get(key);
        } else
            return null;
    }

    public Boolean Update(BigInteger key, byte[] value) {
        System.out.println("Update <" + key + "," + new String(value) + ">");
        if (this.database.containsKey(key)) {
            this.database.put(key, value); // Se a chave já existir, o valor dela é sobrescrito
            return true;
        } else {
            return false; // Não existe a chave especificada
        }
    }

    public Boolean Delete(BigInteger key) {
        System.out.println("Delete <" + key + ">");
        // Se a chave existir ele remove e retorna verdadeiro, caso contrário,retorna
        // falso
        if (this.database.containsKey(key)) {
            this.database.remove(key);
            return true;
        } else {
            return false;
        }
    }
}
