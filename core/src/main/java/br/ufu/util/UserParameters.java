package br.ufu.util;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class UserParameters {

    private static final String START_PARAMETER_CHARS = "--";
    private static final String SPACE = " ";
    private static final String LINE_BREAK = "\n";
    private static final String ATRIBUTE_DELIMITER = "=";
    private static final Logger log = LogManager.getLogger(UserParameters.class);
    private Configuration configuration;

    public UserParameters() {
        this(new String[0]);
    }

    public UserParameters(String[] args) {
        init(args);
    }

    private static Map<String, String> parseArgs(String[] commandArgs) {
        Map<String, String> args = new HashMap<>();
        String text = String.join(SPACE, ofNullable(commandArgs).orElse(new String[0]));
        if (StringUtils.isBlank(text)) {
            return args;
        }
        String[] strings = StringUtils.replace(text, LINE_BREAK, SPACE).split(SPACE);
        Arrays.stream(strings).forEach(s -> {
            String[] commandSplited = s.split(ATRIBUTE_DELIMITER);
            args.put(commandSplited[0].replace(START_PARAMETER_CHARS, ""),
                    commandSplited.length > 1 ? commandSplited[1] : "");
        });
        return args;
    }

    public Integer getInt(String property) {
        return configuration.getInt(property);
    }

    public String get(String s) {
        return configuration.getString(s);
    }

    public List<Object> getList(String property) {
        return configuration.getList(property);
    }

    private void init(String[] args) {

        Parameters params = new Parameters();

        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class);

        builder.configure(params.properties()
                .setFileName(Constants.APPLICATION_PROPERTIES));

        try {
            configuration = builder.getConfiguration();
            parseArgs(args).forEach((k, v) -> configuration.addProperty(k, v));
        } catch (ConfigurationException cex) {
            log.warn("Could not get the configuration properties", cex);
        }

    }
}
