package server;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class SerializeState {

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static Operation readOperation( byte[] data ) throws IOException {

        byte[] opBy = SerializeState.getBytes(data, 0, 1);
        Integer op = SerializeState.readInt(opBy);

        byte[] tamKeyBy = SerializeState.getBytes(data, 1, 1);
        Integer tamKey = SerializeState.readInt(tamKeyBy);

        byte[] keyBy = SerializeState.getBytes(data, 2, tamKey);
        BigInteger key = SerializeState.readBigInt(keyBy);

        byte[] tamValueBy = SerializeState.getBytes(data, 2 + tamKey, 1);
        Integer tamValue = SerializeState.readInt(tamValueBy);

        byte[] valueBy = SerializeState.getBytes(data, 2 + tamKey + 1, tamValue);
        String value = SerializeState.readString(valueBy);

        return new Operation(key, value, op);
    }

    public static String readString(byte[] b) throws IOException {
        return new String(b, StandardCharsets.UTF_16);
    }

    public static BigInteger readBigInt(byte[] b) {
        return new BigInteger(b);
    }

    public static int readInt(byte[] b) {
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        DataInputStream in = new DataInputStream(bis);
        return bis.read();
    }

    public static byte[] getBytes(byte[] b, int init, int tam){
        byte[] ret = new byte[tam];
        int total = init + tam;
        for(int i = init; i < total; i++){
            ret[(i + tam) - total] = b[i];
        }
        return ret;
    }

}
