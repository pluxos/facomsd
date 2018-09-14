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
public class Caso02CrudNOKParallelTest extends BaseTest {

    public Thread testCrudNOKParallel(String key, String key2, int index, int index2, int p1, File tempLogFile) {

        return new Thread(() -> {

            //Dado: Criei as variáveis
            String[] commands = getArgs(tempLogFile, p1);

            Client clientSpy = Mockito.spy(new Client(commands));
            Scanner mockScanner = Mockito.mock(Scanner.class);

            ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10);


            arrayBlockingQueue.offer(String.format("CREATE %s %s", index, key));
            arrayBlockingQueue.offer(String.format("CREATE %s %s", index, key));
            arrayBlockingQueue.offer(String.format("READ %s", index2));
            arrayBlockingQueue.offer(String.format("UPDATE %s %s", index2, key2));
            arrayBlockingQueue.offer(String.format("CREATE %s %s", index2, key2));
            arrayBlockingQueue.offer(String.format("READ %s", index2));
            arrayBlockingQueue.offer(String.format("DELETE %s", index2));
            arrayBlockingQueue.offer("sair");

            //Mockei com spy para simular o input do usuario
            //Também poderei usar estas classes depois
            when(clientSpy.getScanner()).thenReturn(mockScanner);
            when(mockScanner.hasNext()).thenReturn(true);
            when(mockScanner.nextLine()).thenAnswer((Answer<String>) invocation -> arrayBlockingQueue.take());

            await().untilAsserted(() -> {
                Thread tClient = getThread(clientSpy);
                tClient.start();
                tClient.join();

                verifyMessage(String.format("Command RESPONSE: CREATE OK - %s", key));
                verifyMessage(String.format("Command RESPONSE: CREATE NOK - ID %s já cadastrado na base", index));
                verifyMessage(String.format("Command RESPONSE: READ NOK - ID %s inexistente na base", index2));
                verifyMessage(String.format("Command RESPONSE: UPDATE NOK - ID %s inexistente na base", index2));
                verifyMessage(String.format("Command RESPONSE: CREATE OK - %s", key2));
                verifyMessage(String.format("Command RESPONSE: READ OK - %s", key2));
                verifyMessage(String.format("Command RESPONSE: DELETE OK - %s", key2));

            });

        });
    }

    @Test
    public void shouldTestParallel() throws InterruptedException {
        File tempLogFile = getLogFile();

        int paralellThreads = 10;
        int port = 3600;

        String[] commands = getArgs(tempLogFile, port);

        Server server = Mockito.spy(new Server(commands));
        Thread tServer = getThread(server);
        tServer.start();

        List<Thread> threadList = new ArrayList<>();

        int indexCount = 0;

        final AtomicInteger atomicInteger = new AtomicInteger();

        for (int i = 0; i < paralellThreads; i++) {
            String key1 = String.valueOf((char) (indexCount++ + 65));
            String key2 = String.valueOf((char) (indexCount++ + 65));
            Thread thread = testCrudNOKParallel(key1, key2, indexCount - 1, indexCount, port, tempLogFile);
            thread.setUncaughtExceptionHandler((thread1, throwable) -> {
                System.err.println(throwable);
                atomicInteger.addAndGet(1);
            });
            thread.start();
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
            tempLogFile = File.createTempFile("test_nok_", ".log");
            System.out.println(tempLogFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tempLogFile;
    }
}
