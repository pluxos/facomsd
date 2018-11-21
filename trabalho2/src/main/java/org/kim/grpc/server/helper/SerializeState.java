package org.kim.grpc.server.helper;

import org.kim.grpc.server.model.Operation;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Logger;

public class SerializeState {

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static Operation readOperation(byte[] data) throws IOException {

        byte[] operationBy = SerializeState.getBytes(data, 0, 1);
        Integer operation = SerializeState.readInt(operationBy);

        byte[] keyLengthBy = SerializeState.getBytes(data, 1, 1);
        Integer keyLength = SerializeState.readInt(keyLengthBy);

        byte[] keyBy = SerializeState.getBytes(data, 2, keyLength);
        BigInteger key = SerializeState.readBigInt(keyBy);

        byte[] valueLengthBy = SerializeState.getBytes(data, 2 + keyLength, 1);
        Integer valueLength = SerializeState.readInt(valueLengthBy);

        byte[] valueBy = SerializeState.getBytes(data, 2 + keyLength + 1, valueLength);
        String value = SerializeState.readString(valueBy);

        return new Operation(key, value, operation);
    }

    public static String readString(byte[] b) throws IOException {
        return new String(b,"UTF-16");
    }

    public static BigInteger readBigInt(byte[] b) { return new BigInteger(b); }

    public static int readInt(byte[] b) {
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        DataInputStream in = new DataInputStream(bis);

        return bis.read();
    }

    public static byte[] getBytes(byte[] b, int init, int length){
        byte[] ret = new byte[length];
        int total = init + length;

        for (int i = init; i < total; i++) ret[(i + length) - total] = b[i];

        return ret;
    }
}
