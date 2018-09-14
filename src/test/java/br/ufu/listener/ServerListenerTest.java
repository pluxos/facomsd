package br.ufu.listener;

import br.ufu.model.Command;
import br.ufu.service.QueueService;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static br.ufu.util.Constants.PROPERTY_SERVER_PORT;
import static br.ufu.util.UserParameters.getInt;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ServerListenerTest {

    private static final String IP = "127.0.0.1";
    private static final String COMMAND = "COMMAND";

    @Test
    public void shouldProduceCommand() {

        QueueService queueService = new QueueService();
        QueueService queueServiceSpy = Mockito.spy(queueService);

        Integer serverPort = getInt(PROPERTY_SERVER_PORT);
        ServerListener serverListener = new ServerListener(queueServiceSpy, serverPort);
        Thread t = new Thread(serverListener);
        t.start();

        Thread clientThread = new Thread(() -> {
            TestServerClient client = new TestServerClient();
            try {
                client.startConnection(IP, serverPort);
                client.sendMessage(COMMAND);
                client.stopConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        clientThread.start();

        await().untilAsserted(() -> {
            Command result = queueService.consumeF1();
            result.getClientHandler().close();

            assertEquals(COMMAND, result.getExecuteCommand());
            verify(queueServiceSpy, times(1)).produceF1(result);
        });

    }

}
