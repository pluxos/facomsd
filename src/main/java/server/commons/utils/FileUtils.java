package server.commons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileUtils {

	public static Properties getConfigProperties() throws IOException {
		InputStream input = FileUtils.class.getClassLoader().getResourceAsStream("config.properties");
		Properties properties = new Properties();
        properties.load(input);
        return properties;
	}
}