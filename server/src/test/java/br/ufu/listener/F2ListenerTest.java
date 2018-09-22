package br.ufu.listener;

import br.ufu.model.Command;
import br.ufu.service.QueueService;
import br.ufu.writer.LogWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

public class F2ListenerTest {

    private static final String ITEM = "READ 1";
    private static final String SEPARATOR = "\n";

    @Test
    public void shouldListenToF2AndWriteToLog() throws IOException {

        QueueService queueService = new QueueService();
        Command command = new Command(ITEM, null);
        queueService.produceF2(command);

        File tempFile = File.createTempFile("log_test_", ".txt");
        LogWriter logWriter = new LogWriter(tempFile.getAbsolutePath());

        Thread t = new Thread(new F2Listener(queueService, logWriter));
        t.start();

        await().untilAsserted(() -> {
            assertEquals(ITEM + SEPARATOR, readFileToString(tempFile));
        });

    }

}
