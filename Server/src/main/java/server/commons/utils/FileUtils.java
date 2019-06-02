package server.commons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

public class FileUtils {

	public static Properties getConfigProperties() throws IOException {
		InputStream input = FileUtils.class.getClassLoader().getResourceAsStream("config.properties");
		Properties properties = new Properties();
        properties.load(input);
        return properties;
	}
	
	public static String read(String absolutePathName) throws IOException {

		StringBuilder fileStr = new StringBuilder("");

		try (Stream<String> stream = Files.lines(Paths.get(absolutePathName))) {
			stream.forEach(line -> fileStr.append(line));
		}
		return fileStr.toString();
	}
}