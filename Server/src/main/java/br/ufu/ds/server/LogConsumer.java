package br.ufu.ds.server;

import br.ufu.ds.Config;
import br.ufu.ds.rpc.Request;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Marcus
 * @author Lucas Tannus
 * @author Isadora Lopes
 *
 */
public final class LogConsumer extends Consumer {

    private FileOutputStream mLogFile;
    private int lastFileNumber = 0;

    public LogConsumer() {
        super(Queues.getInstance().getLogs());
    }

    @Override
    public void consume(Queues.Command command) {
        Request request = command.request;
        int fileNumber = SnapshotLog.getInstance().getCurrentSnap();
        String fileName = String.format("log.%d.bin", fileNumber - 1);

        try {
            if (lastFileNumber != fileNumber) {
                lastFileNumber = fileNumber;
                mLogFile = new FileOutputStream(fileName, true);
            }

            request.writeDelimitedTo(mLogFile);
            mLogFile.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // delete oldest logs
        int logs = FileUtils.listFiles(new File(SnapshotLog.getInstance().currentDirectory),
                new String[]{"bin"}, false).size();


        if (logs >= Config.getInstance().getLogFactor()) {
            // delete oldest file (snap and log)
            int number = fileNumber - 1 - Config.getInstance().getLogFactor();
            String logName = String.format("log.%d.bin", number);
            File fileToDelete = new File(logName);

            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
        }
    }
}