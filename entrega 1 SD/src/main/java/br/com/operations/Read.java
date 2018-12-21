package br.com.operations;

import br.com.type.Dado;
import io.atomix.copycat.Query;

import java.math.BigInteger;

public class Read implements Query<Dado> {
    public BigInteger key;
    public String valor;

    public Read(BigInteger key, String valor) {
        this.key = key;
        this.valor = valor;
    }
}
