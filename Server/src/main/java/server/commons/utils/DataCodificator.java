package server.commons.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class DataCodificator {

	public static BigInteger stringToBigInteger(String str) {
		return new BigInteger(str.getBytes(StandardCharsets.UTF_8));
	}

	public static byte[] stringToByteArray(String str) {
		return str.getBytes(StandardCharsets.UTF_8);
	}
}
