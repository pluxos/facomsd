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
//
//    private static final String ITEM = "CREATE 1";
//    private static final String SEPARATOR = "\n";
//    private static final String ITEM_READ = "READ 1";
//
//    @Test
//    public void shouldListenToF2AndWriteToLog() throws IOException {
//        testF2(ITEM, ITEM + SEPARATOR);
//    }
//
//
//    @Test
//    public void shouldListenToF2AndDontWriteToLog() throws IOException {
//        testF2(ITEM_READ, "");
//    }
//
//    private void testF2(String itemRead, String logContent) throws IOException {
//        QueueService queueService = new QueueService();
//        Command command = new Command(itemRead, null);
//        queueService.produceF2(command);
//
//        File tempFile = File.createTempFile("log_test_", ".txt");
//        LogWriter logWriter = new LogWriter(tempFile.getAbsolutePath());
//
//        Thread t = new Thread(new F2Listener(queueService, logWriter));
//        t.start();
//
//        await().untilAsserted(() -> {
//            assertEquals(logContent, readFileToString(tempFile));
//        });
//    }

}
