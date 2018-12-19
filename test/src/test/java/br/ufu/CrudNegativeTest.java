package br.ufu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static br.ufu.TestUtil.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CrudNegativeTest extends BaseTest {

    @Test
    public void shouldTestCrudNegative() throws Exception {

        deleteLogsAndSnapshots();
        deleteAtomixLogs();

        List<Thread> servers =  initServers(4, 4, 4444, 100000, 3);

        for (Thread thread: servers) {
            thread.start();
            thread.sleep(3000);
        }

        String[] commands = getClientArgs(4445);
        Client clientSpy = Mockito.spy(new Client(commands));

        Scanner mockScanner = Mockito.mock(Scanner.class);

        BlockingQueue<String> inputs = new ArrayBlockingQueue<>(50);

        inputs.offer("CREATE -1 X");
        inputs.offer("READ -121");
        inputs.offer("UPDATE -991 Y");
        inputs.offer("DELETE -1123");
        inputs.offer("sair");

        when(clientSpy.getScanner()).thenReturn(mockScanner);
        when(mockScanner.hasNext()).thenAnswer((Answer<Boolean>) invocation -> true);
        when(mockScanner.nextLine()).thenAnswer((Answer<String>) invocation -> {
            Thread.sleep(3000);
            return inputs.take();
        });

        Thread.sleep(6000);

        Thread tClient = getThread(clientSpy);
        System.out.println("Client started!");
        tClient.start();
        tClient.join();

        verifyMessage("Key not supported. Accepted range: [ 0 , 15 ]");
        verifyMessage("Key not supported. Accepted range: [ 0 , 15 ]");
        verifyMessage("Key not supported. Accepted range: [ 0 , 15 ]");
        verifyMessage("Key not supported. Accepted range: [ 0 , 15 ]");

        for (Thread thread: servers) {
            thread.stop();
        }
    }


}
