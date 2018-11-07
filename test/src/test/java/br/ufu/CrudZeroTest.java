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
public class CrudZeroTest extends BaseTest {

    @Test
    public void shouldTestCrudZero() throws Exception {

        deleteLogsAndSnapshots();

        List<Thread> servers =  initServers(6, 11, 4444, 100000);

        for (Thread thread: servers) {
            thread.start();
            thread.sleep(100);
        }

        String[] commands = getClientArgs(4448);
        Client clientSpy = Mockito.spy(new Client(commands));

        Scanner mockScanner = Mockito.mock(Scanner.class);

        BlockingQueue<String> inputs = new ArrayBlockingQueue<>(50);

        inputs.offer("CREATE 0 X");
        inputs.offer("READ 0");
        inputs.offer("UPDATE 0 Y");
        inputs.offer("DELETE 0");
        inputs.offer("sair");

        when(clientSpy.getScanner()).thenReturn(mockScanner);
        when(mockScanner.hasNext()).thenAnswer((Answer<Boolean>) invocation -> true);
        when(mockScanner.nextLine()).thenAnswer((Answer<String>) invocation -> {
            Thread.sleep(500);
            return inputs.take();
        });

        Thread.sleep(2000);

        Thread tClient = getThread(clientSpy);
        System.out.println("Client started!");
        tClient.start();
        tClient.join();

        verifyMessage("Command RESPONSE: CREATE OK - X");
        verifyMessage("Command RESPONSE: READ OK - X");
        verifyMessage("Command RESPONSE: UPDATE OK - Y");
        verifyMessage("Command RESPONSE: DELETE OK - Y");

        for (Thread thread: servers) {
            thread.stop();
        }
    }


}
