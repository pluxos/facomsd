package br.ufu.writer;

import java.io.*;

public class LogWriter {

    private OutputStreamWriter writer;

    public LogWriter(String path) throws FileNotFoundException {
        File file = new File(path);
        writer = new OutputStreamWriter(new FileOutputStream(file));
    }

    public void write(String command) throws IOException {
        writer.write(command);
        writer.flush();
    }

}
