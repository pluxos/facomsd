package br.ufu;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static br.ufu.TestUtil.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CrudNOKTest extends BaseTest {

    @Test
    public void shouldTestCrudNOK() throws Exception {

        deleteLogsAndSnapshots();

        List<Thread> servers =  initServers(4, 4, 4444, 100000, 3);

        for (Thread thread: servers) {
            thread.start();
            thread.sleep(100);
        }

        String[] commands = getClientArgs(4445);
        Client clientSpy = Mockito.spy(new Client(commands));

        Scanner mockScanner = Mockito.mock(Scanner.class);

        BlockingQueue<String> inputs = new ArrayBlockingQueue<>(50);

        inputs.offer("CREATE 12 I");
        inputs.offer("CREATE 12 I");

        inputs.offer("READ 10");

        inputs.offer("CREATE 4 J");
        inputs.offer("CREATE 4 J");

        inputs.offer("DELETE 1");

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

        verifyMessage("Command RESPONSE: CREATE OK - I");
        verifyMessage("Command RESPONSE: CREATE NOK - ID 12 já cadastrado na base");

        verifyMessage("Command RESPONSE: READ NOK - ID 10 inexistente na base");

        verifyMessage("Command RESPONSE: CREATE OK - J");
        verifyMessage("Command RESPONSE: CREATE NOK - ID 4 já cadastrado na base");

        verifyMessage("Command RESPONSE: DELETE NOK - ID 1 inexistente na base");

        for (Thread thread: servers) {
            thread.stop();
        }
    }


}
