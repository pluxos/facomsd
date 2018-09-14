package br.ufu.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class LogWriter {

    private OutputStreamWriter writer;

    public LogWriter(String path) throws IOException {
        File file = new File(path);
        writer = new FileWriter(file, true);
    }

    public void write(String command) throws IOException {
        writer.write(command);
        writer.flush();
    }

}
