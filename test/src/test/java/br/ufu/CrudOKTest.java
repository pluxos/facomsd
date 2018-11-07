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
public class CrudOKTest extends BaseTest {

    @Test
    public void shouldTestCrudOK() throws Exception {

        deleteLogsAndSnapshots();
        Thread.sleep(3000);

        List<Thread> servers =  initServers(4, 4, 4444, 100000);

        for (Thread thread: servers) {
            thread.start();
            thread.sleep(1000);
        }

        String[] commands = getClientArgs(4445);
        Client clientSpy = Mockito.spy(new Client(commands));

        Scanner mockScanner = Mockito.mock(Scanner.class);

        BlockingQueue<String> inputs = new ArrayBlockingQueue<>(50);

        inputs.offer("CREATE 12 I");
        inputs.offer("READ 12");

        inputs.offer("CREATE 9 O");
        inputs.offer("DELETE 9");

        inputs.offer("CREATE 4 J");
        inputs.offer("UPDATE 4 P");

        inputs.offer("CREATE 1 U");
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
        verifyMessage("Command RESPONSE: READ OK - I");

//        verifyMessage("Command RESPONSE: CREATE OK - O");
//        verifyMessage("Command RESPONSE: DELETE OK - O");
//
//        verifyMessage("Command RESPONSE: CREATE OK - J");
//        verifyMessage("Command RESPONSE: UPDATE OK - P");
//
//        verifyMessage("Command RESPONSE: CREATE OK - U");
//        verifyMessage("Command RESPONSE: DELETE OK - U");

        for (Thread thread: servers) {
            thread.stop();
        }
    }


}
