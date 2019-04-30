package server.commons.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.commons.exceptions.ErrorMap;
import server.commons.exceptions.ServerException;

public class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> T deserialize(String json, Class<T> clazz) throws ServerException {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new ServerException(ErrorMap.INTERNAL_SERVER_ERROR);
        }
    }

    public static <T> String serialize(T object) throws ServerException {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ServerException(ErrorMap.INTERNAL_SERVER_ERROR);
        }
    }
}