package br.ufu.service;

import br.ufu.exception.InvalidCommandException;
import br.ufu.repository.CrudRepository;

import java.math.BigInteger;

public class CrudService {

    private static final String SPACE = " ";
    private static final String CREATE = "CREATE";
    private static final String READ = "READ";
    private static final String UPDATE = "UPDATE";
    private static final String DELETE = "DELETE";
    private final CrudRepository crudRepository;

    public CrudService(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public String execute(String command) throws InvalidCommandException {
        String[] commandSplited = command.split(SPACE);
        String option = commandSplited[0];
        switch (option) {
            case CREATE:
                return create(commandSplited);
            case READ:
                return read(commandSplited);
            case UPDATE:
                return update(commandSplited);
            case DELETE:
                return delete(commandSplited);
            default:
                throw new InvalidCommandException(command + " is not a valid command");
        }
    }

    private String delete(String[] commandSplited) throws InvalidCommandException {
        return crudRepository.delete(parseNumber(commandSplited[1]));
    }

    private String update(String[] commandSplited) throws InvalidCommandException {
        return crudRepository.update(parseNumber(commandSplited[1]), commandSplited[2]);
    }

    private String read(String[] commandSplited) throws InvalidCommandException {
        return crudRepository.read(parseNumber(commandSplited[1]));
    }

    private String create(String[] commandSplited) {
        return crudRepository.create(commandSplited[1]);
    }

    private BigInteger parseNumber(String number) throws InvalidCommandException {
        try {
            return new BigInteger(number);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Could not parse command", e);
        }
    }

}
