package br.ufu;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

import static br.ufu.TestUtil.getArgs;
import static br.ufu.TestUtil.getThread;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Caso02OrderTest extends BaseTest {

    @Test
    @Ignore
    public void shouldTestRecover() throws Exception {

        //Dado: Criei as variáveis
        File tempLogFile = File.createTempFile("test_", ".log");
        String[] commands = getArgs(tempLogFile, 4468);

        Server serverSpy = Mockito.spy(new Server(commands));
        Client clientSpy = Mockito.spy(new Client(commands));

        Scanner mockScanner = Mockito.mock(Scanner.class);

        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(50);

        //Mockei com spy para simular o input do usuario
        //Também poderei usar estas classes depois
        when(clientSpy.getScanner()).thenReturn(mockScanner);
        when(mockScanner.hasNext()).thenReturn(true);
        when(mockScanner.nextLine()).thenAnswer((Answer<String>) invocation -> {
            Thread.sleep(500);
            return blockingQueue.take();
        });

        //Start das Threads
        Thread tServer = getThread(serverSpy);
        tServer.start();

        await().dontCatchUncaughtExceptions().untilAsserted(() -> {
            Thread tClient = getThread(clientSpy);
            tClient.start();
            blockingQueue.offer("CREATE 1 1");

            tClient.join();
        });


        //Restart server
        tServer.stop();
        tServer = getThread(serverSpy);
        tServer.start();

        await().dontCatchUncaughtExceptions().untilAsserted(() -> {
            Thread tClient = getThread(clientSpy);
            tClient.start();
            tClient.join();
            verifyMessage("Command RESPONSE: READ OK - I1");
            verifyMessage("Command RESPONSE: READ OK - I2");
            verifyMessage("Command RESPONSE: READ OK - I3");
            verifyMessage("Command RESPONSE: READ OK - I4");
            verifyMessage("Command RESPONSE: READ OK - I5");
        });

        tServer.stop();
    }
}
