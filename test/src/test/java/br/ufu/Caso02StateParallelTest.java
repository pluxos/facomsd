package br.ufu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static br.ufu.TestUtil.getArgs;
import static br.ufu.TestUtil.getThread;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Caso02StateParallelTest extends BaseTest {

    public Thread testRecover(String key, int startIndex, int p1, int p2, File tempLogFile) {

        return new Thread(() -> {

            String[] commands = getArgs(tempLogFile, p1);

            Server serverSpy = Mockito.spy(new Server(commands));
            Client clientSpy = Mockito.spy(new Client(commands));

            Scanner mockScanner = Mockito.mock(Scanner.class);

            ArrayBlockingQueue<String> inputs = new ArrayBlockingQueue<>(10);

            for (int i = startIndex; i < startIndex + 5; i++) {
                inputs.offer(String.format("CREATE %s " + key + "%s", i, i));
            }

            inputs.offer("sair");

            //Mockei com spy para simular o input do usuario
            //Também poderei usar estas classes depois
            when(clientSpy.getScanner()).thenReturn(mockScanner);
            when(mockScanner.hasNext()).thenReturn(true);
            when(mockScanner.nextLine()).thenAnswer((Answer<String>) invocation -> {
                Thread.sleep(50);
                return inputs.take();
            });

            //Start das Threads
            Thread tServer = getThread(serverSpy);
            tServer.start();

            await().dontCatchUncaughtExceptions().untilAsserted(() -> {
                Thread tClient = getThread(clientSpy);
                tClient.start();
                tClient.join();
            });


            stopServer(tServer);
            //Restart server
            commands = getArgs(tempLogFile, p2);

            Server newServerSpy = Mockito.spy(new Server(commands));
            Client newClientSpy = Mockito.spy(new Client(commands));

            when(newClientSpy.getScanner()).thenReturn(mockScanner);

            //Start das Threads
            Thread newTServer = getThread(newServerSpy);
            newTServer.start();


            for (int i = startIndex; i < startIndex + 5; i++) {
                inputs.offer(String.format("READ %s", i));
            }
            inputs.offer("sair");

            await().atLeast(1, TimeUnit.SECONDS).untilAsserted(() -> {
                Thread tClient = getThread(newClientSpy);
                tClient.start();
                tClient.join();
                verifyMessage("Command RESPONSE: READ OK - " + key + "" + (startIndex));
                verifyMessage("Command RESPONSE: READ OK - " + key + "" + (startIndex + 1));
                verifyMessage("Command RESPONSE: READ OK - " + key + "" + (startIndex + 2));
                verifyMessage("Command RESPONSE: READ OK - " + key + "" + (startIndex + 3));
                verifyMessage("Command RESPONSE: READ OK - " + key + "" + (startIndex + 4));
            });

            stopServer(tServer);

        });
    }

    private void stopServer(Thread tServer) {
        try {
            tServer.stop();
        } catch (ThreadDeath e) {
            System.err.println("Ignoring thread death");
        }
    }

    @Test
    public void shouldTestParallel() throws InterruptedException {
        File tempLogFile = getLogFile();

        int paralellThreads = 10;
        int startIndex = 1;
        int startPort = 4470;

        List<Thread> threadList = new ArrayList<>();


        for (int i = 0; i < paralellThreads; i++) {
            String key = String.valueOf((char) (i + 65));
            Thread thread = testRecover(key, startIndex, startPort++, startPort++, tempLogFile);
            thread.start();
            threadList.add(thread);
            startIndex += 5;
        }

        for (Thread thread : threadList) {
            thread.join();
        }

    }

    private File getLogFile() {
        //Dado: Criei as variáveis
        File tempLogFile = null;
        try {
            tempLogFile = File.createTempFile("test_state_", ".log");
            System.out.println(tempLogFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tempLogFile;
    }
}
