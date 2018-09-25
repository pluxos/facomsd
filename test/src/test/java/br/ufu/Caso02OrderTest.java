package br.ufu;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static br.ufu.TestUtil.getArgs;
import static br.ufu.TestUtil.getThread;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Caso02OrderTest extends BaseTest {

    @Test
    public void shouldTestOrder() throws Exception {

        //Dado: Criei as variáveis
        File tempLogFile = File.createTempFile("test_order_", ".log");
        String[] commands = getArgs(tempLogFile, 4460);

        Server serverSpy = Mockito.spy(new Server(commands));
        Client clientSpy = Mockito.spy(new Client(commands));

        Scanner mockScanner = Mockito.mock(Scanner.class);

        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(50);

        //Mockei com spy para simular o input do usuario
        //Também poderei usar estas classes depois
        when(clientSpy.getScanner()).thenReturn(mockScanner);
        when(mockScanner.hasNext()).thenReturn(true);
        when(mockScanner.nextLine()).thenAnswer((Answer<String>) invocation -> blockingQueue.take());

        //Start das Threads
        Thread tServer = getThread(serverSpy);
        tServer.start();


        await().dontCatchUncaughtExceptions().atMost(2, TimeUnit.MINUTES).untilAsserted(() -> {
            Thread tClient = getThread(clientSpy);
            tClient.start();
            blockingQueue.offer("CREATE 0 1");


            for (int i = 1; i <= 100; i++) {
                int finalI = i;
                await().untilAsserted(() -> {
                    assert getLogSizeWithoutReads() == finalI;
                    blockingQueue.offer("READ " + (finalI - 1));
                    String lastValue = StringUtils.strip(getLastLog().split("-")[1]);
                    blockingQueue.offer(String.format("CREATE %s %s", finalI, Integer.parseInt(lastValue) + 1));
                });
            }


            await().untilAsserted(() -> {
                blockingQueue.offer("READ 100");
                String lastValue = StringUtils.strip(getLastLog().split("-")[1]);
                Assert.assertEquals(101, Integer.parseInt(lastValue));

                blockingQueue.offer("sair");

                tClient.join();
            });

        });

        tServer.stop();
    }
}
