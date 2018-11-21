package org.kim.grpc.client.factory;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class Operation {

    private BigInteger key;
    private String value;
    private Integer command;

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Operation(BigInteger key, String value, Integer command) {
        this.key = key;
        this.value = value;
        this.command = command;
    }

    public byte[] convertData(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        int keyLength;
        int messageLength = 0;

        try {
            keyLength = key.toByteArray().length;
            if (value != null) messageLength = value.getBytes(StandardCharsets.UTF_16).length-2;

            dos.write(command);

            dos.write(keyLength);

            dos.write(key.toByteArray());

            dos.write(messageLength);

            if (value != null) dos.writeChars(value);

            dos.flush();

        } catch (IOException e) { e.printStackTrace(); }

        return bos.toByteArray();
    }
}
