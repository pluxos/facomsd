package br.ufu.service;

import br.ufu.exception.InvalidCommandException;
import br.ufu.repository.CrudRepository;
import br.ufu.repository.DatabaseException;

import static br.ufu.util.Constants.BIG_INTEGER_PARSER;

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
        try {

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
        } catch (DatabaseException e) {
            throw new InvalidCommandException(e.getMessage());
        }
    }

    private String delete(String[] commandSplited) throws InvalidCommandException, DatabaseException {
        return crudRepository.delete(BIG_INTEGER_PARSER.parse(commandSplited[1]));
    }

    private String update(String[] commandSplited) throws InvalidCommandException, DatabaseException {
        return crudRepository.update(BIG_INTEGER_PARSER.parse(commandSplited[1]), commandSplited[2]);
    }

    private String read(String[] commandSplited) throws InvalidCommandException, DatabaseException {
        return crudRepository.read(BIG_INTEGER_PARSER.parse(commandSplited[1]));
    }

    public String create(String[] commandSplited) throws InvalidCommandException, DatabaseException {
        return crudRepository.create(BIG_INTEGER_PARSER.parse(commandSplited[1]), commandSplited[2]);
    }


}
