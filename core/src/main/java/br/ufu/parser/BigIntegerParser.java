package br.ufu.parser;

import br.ufu.exception.InvalidCommandException;

import java.math.BigInteger;

public class BigIntegerParser implements Parser<BigInteger> {

    @Override
    public BigInteger parse(String value) throws InvalidCommandException {
        try {
            return new BigInteger(value);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Could not parse command", e);
        }
    }
}
