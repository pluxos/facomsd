package br.ufu;

import org.junit.Assert;
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
import java.util.concurrent.atomic.AtomicInteger;

import static br.ufu.TestUtil.getArgs;
import static br.ufu.TestUtil.getThread;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Caso02CrudOKParallelTest extends BaseTest {

    public Thread testCrudOKParallel(String key, int index, int p1, File tempLogFile) {

        return new Thread(() -> {

            String[] commands = getArgs(tempLogFile, p1);

            ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10);

            arrayBlockingQueue.offer(String.format("CREATE %s %s", index, key));
            arrayBlockingQueue.offer(String.format("READ %s", index));
            arrayBlockingQueue.offer(String.format("UPDATE %s %sU", index, key));
            arrayBlockingQueue.offer(String.format("READ %s", index));
            arrayBlockingQueue.offer(String.format("DELETE %s", index));
            arrayBlockingQueue.offer("sair");

            Scanner mockScanner = Mockito.mock(Scanner.class);
            Client clientSpy = Mockito.spy(new Client(commands));
            //Mockei com spy para simular o input do usuario
            //Também poderei usar estas classes depois
            when(clientSpy.getScanner()).thenReturn(mockScanner);
            when(mockScanner.hasNext()).thenReturn(true);
            when(mockScanner.nextLine()).thenAnswer((Answer<String>) invocation -> arrayBlockingQueue.take());

            //Start das Threads
            await().untilAsserted(() -> {
                Thread tClient = getThread(clientSpy);
                tClient.start();

                tClient.join();

                //As seguintes repostas deverão ser logadas
                verifyMessage(String.format("Command RESPONSE: CREATE OK - %s", key));
                verifyMessage(String.format("Command RESPONSE: READ OK - %s", key));
                verifyMessage(String.format("Command RESPONSE: UPDATE OK - %sU", key));
                verifyMessage(String.format("Command RESPONSE: READ OK - %sU", key));
                verifyMessage(String.format("Command RESPONSE: DELETE OK - %sU", key));

            });

        });
    }

    @Test
    public void shouldTestParallel() throws InterruptedException {
        File tempLogFile = getLogFile();

        int paralellThreads = 10;
        int port = 3500;

        String[] commands = getArgs(tempLogFile, port);

        Server server = Mockito.spy(new Server(commands));
        Thread tServer = getThread(server);
        tServer.start();


        List<Thread> threadList = new ArrayList<>();

        final AtomicInteger atomicInteger = new AtomicInteger();

        for (int i = 0; i < paralellThreads; i++) {
            String key = String.valueOf((char) (i + 65));
            Thread thread = testCrudOKParallel(key, i + 1, port, tempLogFile);
            thread.start();
            thread.setUncaughtExceptionHandler((thread1, throwable) -> atomicInteger.addAndGet(1));
            threadList.add(thread);
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        Assert.assertEquals(0, atomicInteger.get());

        tServer.stop();
    }

    private File getLogFile() {
        //Dado: Criei as variáveis
        File tempLogFile = null;
        try {
            tempLogFile = File.createTempFile("test_ok_", ".log");
            System.out.println(tempLogFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tempLogFile;
    }
}
