package client.commons.utils;

public class DataCodificator {
	public static String prepareInputs(String[] inputParams) {
        return "email: " + inputParams[2]
                + " password: " + inputParams[3]
                + " name: " + inputParams[4];
    }
}
