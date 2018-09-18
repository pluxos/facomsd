package br.ufu.parser;

import br.ufu.exception.InvalidCommandException;

public interface Parser<E> {
    E parse(String value) throws InvalidCommandException;
}
