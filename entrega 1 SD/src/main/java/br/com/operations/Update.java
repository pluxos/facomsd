package br.com.operations;

import io.atomix.copycat.Command;

import java.math.BigInteger;

public class Update implements Command<Boolean> {
    public BigInteger key;
    public String valor;

    public Update(BigInteger key, String valor) {
        this.key = key;
        this.valor = valor;
    }
}
