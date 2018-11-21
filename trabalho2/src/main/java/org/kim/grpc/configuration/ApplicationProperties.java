package org.kim.grpc.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ApplicationProperties {

    private static ApplicationProperties applicationProperties;
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static ApplicationProperties getInstance(){
        return applicationProperties == null ? new ApplicationProperties(): applicationProperties;
    }

    public Properties loadProperties(){
        Properties prop = new Properties();
        InputStream input;
        String PROP_NAME = "application.properties";
        try {
            input = getClass().getClassLoader().getResourceAsStream(PROP_NAME);
            if (input != null) prop.load(input);
            else throw new FileNotFoundException("property file '" + PROP_NAME + "' not found in the classpath");

        } catch (IOException e) {
            logger.warning(e.getMessage());
            return null;
        }
        return prop;
    }
}
