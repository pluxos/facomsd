package br.ufu.util;

import br.ufu.parser.BigIntegerParser;
import br.ufu.parser.Parser;
import br.ufu.parser.StringParser;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("squid:S2386Mutable")
public class Constants {

    public static final int MAX_QUEUE_ITEMS = 50;
    public static final String COMMAND_SEPARATOR = " ";
    public static final String MESSAGE_INVALID_COMMAND = "[%s] is not a valid command. Valid commands are: %s";
    public static final String PROPERTY_SERVER_HOST = "server.host";
    public static final String PROPERTY_SERVER_PORT = "server.port";
    public static final String PROPERTY_SNAP_TIME = "snap.time";
    public static final String PROPERTY_SNAP_PATH = "snap.path";
    public static final String PROPERTY_LOG_PATH = "log.path";
    public static final String PROPERTY_SMALLER_KEY = "smaller.key";
    public static final String PROPERTY_SERVER_ID = "server.id";
    public static final String PROPERTY_RIGHT_SERVER = "right.server";
    public static final String PROPERTY_LEFT_SERVER = "left.server";
    public static final String PROPERTY_MAX_KEY = "max.key";
    public static final Parser<BigInteger> BIG_INTEGER_PARSER = new BigIntegerParser();
    public static final String MESSAGE_PARAMETROS_INSUFICIENTES = "Parâmetros insuficientes";
    public static final Parser<String> STRING_PARSER = new StringParser();
    public static final String COMMAND_CREATE = "CREATE";
    public static final String COMMAND_READ = "READ";
    public static final String COMMAND_UPDATE = "UPDATE";
    public static final String COMMAND_DELETE = "DELETE";
    public static final String COMMAND_SAIR = "sair";
    static final String APPLICATION_PROPERTIES = "application.properties";
    private static final List<String> VALID_COMMANDS = Arrays.asList(COMMAND_CREATE, COMMAND_READ, COMMAND_UPDATE, COMMAND_DELETE);

    private Constants() {

    }

    public static List<String> getValidCommands() {
        return new ArrayList<>(VALID_COMMANDS);
    }
}
