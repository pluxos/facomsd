package client.commons.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	private static ObjectMapper mapper = new ObjectMapper();
	
	public static <T> T deserialize(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			return null;
		}
	}
	
	public static <T> String serialize(T object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}