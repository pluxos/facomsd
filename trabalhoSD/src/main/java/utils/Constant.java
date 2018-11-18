package utils;

import java.math.BigInteger;

public class Constant {
	public static int MAX_QUEUE = 2024;
	public static final int QUEUE_INCREMENT_RESIZE = (int) (MAX_QUEUE*0.03);
	//public static final int SERVER_PORT = 9876;
	public static final String HOST = "127.0.0.1";
	public static BigInteger maxKey = new BigInteger("5000");
	public static BigInteger minKey= new BigInteger("0");
}
