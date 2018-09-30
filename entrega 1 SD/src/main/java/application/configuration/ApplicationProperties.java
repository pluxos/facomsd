package application.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties {

    public Properties getProp() throws IOException {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream(
                "/home/thiago/Área de Trabalho/entrega 1 SD/src/main/properties/application.properties");
        props.load(file);
        return props;

    }

}


