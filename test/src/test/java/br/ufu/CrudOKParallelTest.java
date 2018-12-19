package br.ufu;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static br.ufu.TestUtil.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CrudOKParallelTest extends BaseTest {

    public Thread testCrudOKParallel(String key, int index, int port) {

        return new Thread(() -> {
            try {
                String[] commands = getClientArgs(port);
                Client clientSpy = Mockito.spy(new Client(commands));

                Scanner mockScanner = Mockito.mock(Scanner.class);

                BlockingQueue<String> inputs = new ArrayBlockingQueue<>(50);

                inputs.offer(String.format("CREATE %s %s", index, key));
                inputs.offer(String.format("READ %s", index));
                inputs.offer(String.format("UPDATE %s %sU", index, key));
                inputs.offer(String.format("READ %s", index));
                inputs.offer(String.format("DELETE %s", index));
                inputs.offer("sair");


                when(clientSpy.getScanner()).thenReturn(mockScanner);
                when(mockScanner.hasNext()).thenAnswer((Answer<Boolean>) invocation -> true);
                when(mockScanner.nextLine()).thenAnswer((Answer<String>) invocation -> {
                    Thread.sleep(3000);
                    return inputs.take();
                });

                Thread.sleep(3000);

                Thread tClient = getThread(clientSpy);
                System.out.println("Client started!");
                tClient.start();
                tClient.join();

                verifyMessage(String.format("Command RESPONSE: CREATE OK - %s", key));
                verifyMessage(String.format("Command RESPONSE: READ OK - %s", key));
                verifyMessage(String.format("Command RESPONSE: UPDATE OK - %sU", key));
                verifyMessage(String.format("Command RESPONSE: READ OK - %sU", key));
                verifyMessage(String.format("Command RESPONSE: DELETE OK - %sU", key));

            } catch ( InterruptedException e) {
                System.err.println("EROU" + e);
            }
        });
    }


    @Test
    public void shouldTestParallel() throws InterruptedException, IOException {

        deleteLogsAndSnapshots();
        deleteAtomixLogs();

        int paralellThreads = 10;
        int port = 4445;

        List<Thread> servers =  initServers(6, 6, 4444, 100000, 3);

        for (Thread thread: servers) {
            thread.start();
            Thread.sleep(100);
        }
        Thread.sleep(1000);

        List<Thread> threadList = new ArrayList<>();

        final AtomicInteger atomicInteger = new AtomicInteger();

        for (int i = 0; i < paralellThreads; i++) {
            String key = String.valueOf((char) (i + 65));
            Thread thread = testCrudOKParallel(key, i + 1, port);
            thread.start();
            thread.setUncaughtExceptionHandler((thread1, throwable) -> atomicInteger.addAndGet(1));
            threadList.add(thread);
        }

        for (Thread thread : threadList) {
            thread.join();
        }
        Assert.assertEquals(0, atomicInteger.get());

        for (Thread thread: servers) {
            thread.stop();
        }

    }


}
