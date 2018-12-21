package br.com.operations;

import io.atomix.copycat.Command;

import java.math.BigInteger;

public class Creat implements Command<Boolean> {
    public BigInteger key;
    public String valor;

    public Creat(BigInteger key, String valor) {
        this.key = key;
        this.valor = valor;
    }
}
