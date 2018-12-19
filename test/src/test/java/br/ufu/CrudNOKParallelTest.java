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
public class CrudNOKParallelTest extends BaseTest {

    public Thread testCrudNOKParallel(String key, String key2, int index, int index2, int port) {

        return new Thread(() -> {
            try {
                String[] commands = getClientArgs(port);
                Client clientSpy = Mockito.spy(new Client(commands));

                Scanner mockScanner = Mockito.mock(Scanner.class);

                BlockingQueue<String> inputs = new ArrayBlockingQueue<>(50);

                inputs.offer(String.format("CREATE %s %s", index, key));
                inputs.offer(String.format("CREATE %s %s", index, key));
                inputs.offer(String.format("READ %s", index2));
                inputs.offer(String.format("UPDATE %s %s", index2, key2));
                inputs.offer(String.format("CREATE %s %s", index2, key2));
                inputs.offer(String.format("READ %s", index2));
                inputs.offer(String.format("DELETE %s", index2));
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
                verifyMessage(String.format("Command RESPONSE: CREATE NOK - ID %s já cadastrado na base", index));
                verifyMessage(String.format("Command RESPONSE: READ NOK - ID %s inexistente na base", index2));
                verifyMessage(String.format("Command RESPONSE: UPDATE NOK - ID %s inexistente na base", index2));
                verifyMessage(String.format("Command RESPONSE: CREATE OK - %s", key2));
                verifyMessage(String.format("Command RESPONSE: READ OK - %s", key2));
                verifyMessage(String.format("Command RESPONSE: DELETE OK - %s", key2));

            } catch ( InterruptedException e) {
                System.err.println(e);
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

        int indexCount = 0;

        final AtomicInteger atomicInteger = new AtomicInteger();

        for (int i = 0; i < paralellThreads; i++) {
            String key1 = String.valueOf((char) (indexCount++ + 65));
            String key2 = String.valueOf((char) (indexCount++ + 65));
            Thread thread = testCrudNOKParallel(key1, key2, indexCount - 1, indexCount, port);
            thread.setUncaughtExceptionHandler((thread1, throwable) -> {
                System.err.println(throwable);
                atomicInteger.addAndGet(1);
            });
            thread.start();
            threadList.add(thread);
            thread.sleep(1000);
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
