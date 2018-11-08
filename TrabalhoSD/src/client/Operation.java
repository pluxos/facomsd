package client;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

class Operation {

    private BigInteger key;
    private String value;
    private Integer cmd;

    Operation(BigInteger key, String value, Integer cmd) {
        this.key = key;
        this.value = value;
        this.cmd = cmd;
    }

    byte[] convertData() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        int keyLength;
        int messageLength = 0;

        try {
            keyLength = key.toByteArray().length;
            if ( value != null ) messageLength = value.getBytes( StandardCharsets.UTF_16 ).length - 2;

            dataOutputStream.write( cmd );

            dataOutputStream.write( keyLength );

            dataOutputStream.write( key.toByteArray() );

            dataOutputStream.write( messageLength );

            if( value != null ) dataOutputStream.writeChars( value );

            dataOutputStream.flush();

        } catch (IOException e) { e.printStackTrace(); }

        return byteArrayOutputStream.toByteArray();
    }
}
