package br.ufu;

import org.awaitility.core.ConditionTimeoutException;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static br.ufu.TestUtil.getArgs;
import static br.ufu.TestUtil.getThread;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Caso02StateParallelTest extends BaseTest {

    public Thread testRecoverCreate(String key, int startIndex, int p1, File tempLogFile) {

        return new Thread(() -> {

            String[] commands = getArgs(tempLogFile, p1);

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
            when(mockScanner.nextLine()).thenAnswer((Answer<String>) invocation -> inputs.take());

            await().dontCatchUncaughtExceptions().untilAsserted(() -> {
                Thread tClient = getThread(clientSpy);
                tClient.start();
                tClient.join();
            });
        });
    }

    public Thread testRecoverRead(String key, int startIndex, int p2, File tempLogFile) {

        return new Thread(() -> {
            //Restart server
            String[] commands = getArgs(tempLogFile, p2);

            Client newClientSpy = Mockito.spy(new Client(commands));

            Scanner mockScanner = Mockito.mock(Scanner.class);

            ArrayBlockingQueue<String> inputs = new ArrayBlockingQueue<>(10);

            when(newClientSpy.getScanner()).thenReturn(mockScanner);
            //Mockei com spy para simular o input do usuario
            //Também poderei usar estas classes depois
            when(newClientSpy.getScanner()).thenReturn(mockScanner);
            when(mockScanner.hasNext()).thenReturn(true);
            when(mockScanner.nextLine()).thenAnswer((Answer<String>) invocation -> inputs.take());

            for (int i = startIndex; i < startIndex + 5; i++) {
                inputs.offer(String.format("READ %s", i));
            }
            inputs.offer("sair");

            await().atMost(1, TimeUnit.SECONDS).untilAsserted(() -> {
                Thread tClient = getThread(newClientSpy);
                tClient.start();
                tClient.join();
                verifyMessage("Command RESPONSE: READ OK - " + key + "" + (startIndex));
                verifyMessage("Command RESPONSE: READ OK - " + key + "" + (startIndex + 1));
                verifyMessage("Command RESPONSE: READ OK - " + key + "" + (startIndex + 2));
                verifyMessage("Command RESPONSE: READ OK - " + key + "" + (startIndex + 3));
                verifyMessage("Command RESPONSE: READ OK - " + key + "" + (startIndex + 4));
            });

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

        final AtomicInteger atomicInteger = new AtomicInteger();

        startForCreate(tempLogFile, paralellThreads, startIndex, 3700, atomicInteger);
        startForRead(tempLogFile, paralellThreads, startIndex, 3701, atomicInteger);

        Assert.assertEquals(0, atomicInteger.get());


    }

    private void startForCreate(File tempLogFile, int paralellThreads, int startIndex, int port, AtomicInteger atomicInteger) throws InterruptedException {
        String[] commands = getArgs(tempLogFile, port);
        Server serverSpy = Mockito.spy(new Server(commands));

        //Start das Threads
        Thread tServer = getThread(serverSpy);
        tServer.start();

        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < paralellThreads; i++) {
            String key = String.valueOf((char) (i + 65));
            Thread thread = testRecoverCreate(key, startIndex, port, tempLogFile);
            thread.setUncaughtExceptionHandler((thread1, throwable) -> {
                if (throwable.getClass().equals(ConditionTimeoutException.class)) {
                    atomicInteger.addAndGet(1);
                }
            });
            thread.start();
            threadList.add(thread);
            startIndex += 5;
        }


        for (Thread thread : threadList) {
            thread.join();
        }

        stopServer(tServer);
    }

    private void startForRead(File tempLogFile, int paralellThreads, int startIndex, int port, AtomicInteger atomicInteger) throws InterruptedException {
        String[] commands = getArgs(tempLogFile, port);
        Server serverSpy = Mockito.spy(new Server(commands));

        //Start das Threads
        Thread tServer = getThread(serverSpy);
        tServer.start();

        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < paralellThreads; i++) {
            String key = String.valueOf((char) (i + 65));
            Thread thread = testRecoverRead(key, startIndex, port, tempLogFile);
            thread.setUncaughtExceptionHandler((thread1, throwable) -> {
                if (throwable.getClass().equals(ConditionTimeoutException.class)) {
                    atomicInteger.addAndGet(1);
                }
            });
            thread.start();
            threadList.add(thread);
            startIndex += 5;
        }


        for (Thread thread : threadList) {
            thread.join();
        }

        stopServer(tServer);
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
