package br.ufu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static br.ufu.TestUtil.*;
import static java.nio.charset.Charset.defaultCharset;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Caso01CrudNOKTest extends BaseTest {

    @Test
    public void shouldTestCrudNOK() throws Exception {

        //Dado: Criei as variáveis

        List<Thread> servers =  initServers(4, 4, 4444, 10000);

        for (Thread thread: servers) {
            thread.start();
            thread.sleep(1000);
        }

//        Thread.sleep(100000);

        String[] commands = getClientArgs(4445);
        Client clientSpy = Mockito.spy(new Client(commands));

        Scanner mockScanner = Mockito.mock(Scanner.class);

        List<String> inputs = new ArrayList<>();
        inputs.add("CREATE 10 I");
        inputs.add("CREATE 10 I");
        inputs.add("READ 5");
        inputs.add("UPDATE 5 J");
        inputs.add("CREATE 5 J");
        inputs.add("READ 5");
        inputs.add("DELETE 5");
        inputs.add("sair");

        final int[] currentInput = {0};

        //Mockei com spy para simular o input do usuario
        when(clientSpy.getScanner()).thenReturn(mockScanner);
        when(mockScanner.hasNext()).thenAnswer((Answer<Boolean>) invocation -> currentInput[0] < inputs.size());
        when(mockScanner.nextLine()).thenAnswer((Answer<String>) invocation -> inputs.get(currentInput[0]++));

        await().dontCatchUncaughtExceptions().untilAsserted(() -> {
            Thread tClient = getThread(clientSpy);
            System.out.println("Client started!");
            tClient.start();

            tClient.join();

//        O Arquivo de Log deve ser escrito
//            String logCommands = splitCommads(
//                    "CREATE 1 I",
//                    "CREATE 1 I",
//                    "UPDATE 2 J",
//                    "CREATE 2 J",
//                    "DELETE 2");

//            assertEquals(logCommands, readFileToString(tempLogFile, defaultCharset()));

//            verifyMessage("Command RESPONSE: CREATE OK - I");
//            verifyMessage("Command RESPONSE: CREATE NOK - ID 1 já cadastrado na base");
//            verifyMessage("Command RESPONSE: READ NOK - ID 2 inexistente na base");
//            verifyMessage("Command RESPONSE: UPDATE NOK - ID 2 inexistente na base");
//            verifyMessage("Command RESPONSE: CREATE OK - J");
//            verifyMessage("Command RESPONSE: READ OK - J");
//            verifyMessage("Command RESPONSE: DELETE OK - J");

        });
        Thread.sleep(100000);
        for (Thread thread: servers) {
            thread.stop();
        }
    }


}
