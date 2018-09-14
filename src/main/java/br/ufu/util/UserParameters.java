package br.ufu.util;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserParameters {

    private static final Logger log = LogManager.getLogger(UserParameters.class);

    private static Configuration configuration;

    private UserParameters() {

    }

    private static void init() {

        if (configuration != null) {
            return;
        }

        Parameters params = new Parameters();

        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class);

        builder.configure(params.properties()
                .setFileName(Constants.APPLICATION_PROPERTIES));
        try {
            configuration = builder.getConfiguration();
        } catch (ConfigurationException cex) {
            log.warn("Could not get the configuration properties", cex);
        }

    }

    public static Integer getInt(String property) {
        init();
        return configuration.getInt(property);
    }

}
