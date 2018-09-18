package br.ufu.util;

import br.ufu.parser.BigIntegerParser;
import br.ufu.parser.Parser;
import br.ufu.parser.StringParser;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final int MAX_QUEUE_ITEMS = 10;
    public static final String COMMAND_SEPARATOR = " ";
    public static final String MESSAGE_INVALID_COMMAND = "[%s] is not a valid command. Valid commands are: %s";
    public static final String PROPERTY_SERVER_HOST = "server.host";
    public static final String PROPERTY_SERVER_PORT = "server.port";
    public static final String PROPERTY_LOG_PATH = "log.path";
    public static final List<String> VALID_COMMANDS = Arrays.asList("CREATE", "READ", "UPDATE", "DELETE");
    public static final Parser<BigInteger> BIG_INTEGER_PARSER = new BigIntegerParser();
    public static final Parser<String> STRING_PARSER = new StringParser();
    public static final String COMMAND_CREATE = "CREATE";
    public static final String COMMAND_READ = "READ";
    public static final String MESSAGE_PARAMETROS_INSUFICIENTES = "Parâmetros insuficientes";
    public static final String COMMAND_UPDATE = "UPDATE";
    public static final String COMMAND_DELETE = "DELETE";
    public static final String COMMAND_SAIR = "sair";
    static final String APPLICATION_PROPERTIES = "application.properties";


    private Constants() {

    }
}
