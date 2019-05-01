package br.ufu.ds.server;

import br.ufu.ds.ServerProtocol;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Marcus
 * @author Lucas Tannus
 * @author Isadora Lopes
 *
 */
public class LogConsumer extends Consumer {

    private FileOutputStream mLogFile;

    public LogConsumer() {
        super(Queues.getInstance().getLogs());
        try {
            mLogFile = new FileOutputStream("log.bin",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void consume(Queues.Command command) {
        ServerProtocol.Request request = command.request;

        try {
            request.writeDelimitedTo(mLogFile);
            mLogFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}