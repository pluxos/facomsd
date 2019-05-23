package server.commons.utils;

import java.math.BigInteger;

public class DataCodificator {

	public static BigInteger stringToBigInteger(String str) {
		return new BigInteger(str.getBytes());
	}

	public static byte[] stringToByteArray(String str) {
		return str.getBytes();
	}
}
