package br.ufu.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;

public class SnapshotWriter {

    private OutputStreamWriter writer;
    private final static String SEPARATOR = " ";
    private final static String BREAK_LINE = "\n";

    public SnapshotWriter(String snapPath, BigInteger snapshotNumber, BigInteger serverId) throws IOException {
        File file = new File(  snapPath + "snaps-server-"
                + serverId.toString() + "/snap." + snapshotNumber.toString() + ".txt");
        file.getParentFile().mkdirs();
        writer = new FileWriter(file, true);
    }

    public void write(BigInteger key, String value) throws IOException {
        writer.write(key + SEPARATOR + value + BREAK_LINE);
        writer.flush();
    }
}
