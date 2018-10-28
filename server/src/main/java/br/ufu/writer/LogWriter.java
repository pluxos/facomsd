package br.ufu.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;

public class LogWriter {

    private OutputStreamWriter writer;

    public LogWriter(String snapPath, BigInteger logNumber, BigInteger serverId) throws IOException {
        File file = new File(  snapPath + "logs-server-"
                + serverId.toString() + "/log." + logNumber.toString() + ".txt");
        file.getParentFile().mkdirs();
        writer = new FileWriter(file, true);
    }

    public void write(String command) throws IOException {
        writer.write(command);
        writer.flush();
    }

    public OutputStreamWriter getWriter() {
        return writer;
    }
}
