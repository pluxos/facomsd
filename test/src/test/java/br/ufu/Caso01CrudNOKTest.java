package br.ufu;

import br.ufu.handler.ClientCommandHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static br.ufu.TestUtil.*;
import static java.nio.charset.Charset.defaultCharset;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Caso01CrudNOKTest extends BaseTest {

    @Test
    public void shouldTestCrudNOK() throws Exception {

        //Dado: Criei as variáveis
        File tempLogFile = File.createTempFile("test_", ".log");
        String[] commands = getArgs(tempLogFile, 4466);

        Server serverSpy = Mockito.spy(new Server(commands));
        Client clientSpy = Mockito.spy(new Client(commands));

        Scanner mockScanner = Mockito.mock(Scanner.class);

        List<String> inputs = new ArrayList<>();
        inputs.add("CREATE 1 I");
        inputs.add("CREATE 1 I");
        inputs.add("READ 2");
        inputs.add("UPDATE 2 J");
        inputs.add("CREATE 2 J");
        inputs.add("READ 2");
        inputs.add("DELETE 2");
        inputs.add("sair");

        final int[] currentInput = {0};

        //Mockei com spy para simular o input do usuario
        //Também poderei usar estas classes depois
        when(clientSpy.getScanner()).thenReturn(mockScanner);
        when(mockScanner.hasNext()).thenReturn(true);
        when(mockScanner.nextLine()).thenAnswer((Answer<String>) invocation -> {
            Thread.sleep(500);
            return inputs.get(currentInput[0]++);
        });

        //Start das Threads
        Thread tServer = getThread(serverSpy);
        tServer.start();

        await().dontCatchUncaughtExceptions().untilAsserted(() -> {
            Thread tClient = getThread(clientSpy);
            tClient.start();

            tClient.join();

//        O Arquivo de Log deve ser escrito
            String logCommands = splitCommads(
                    "CREATE 1 I",
                    "CREATE 1 I",
                    "UPDATE 2 J",
                    "CREATE 2 J",
                    "DELETE 2");

            assertEquals(logCommands, readFileToString(tempLogFile, defaultCharset()));

            verifyMessage("Command RESPONSE: CREATE OK - I");
            verifyMessage("Command RESPONSE: CREATE NOK - ID 1 já cadastrado na base");
            verifyMessage("Command RESPONSE: READ NOK - ID 2 inexistente na base");
            verifyMessage("Command RESPONSE: UPDATE NOK - ID 2 inexistente na base");
            verifyMessage("Command RESPONSE: CREATE OK - J");
            verifyMessage("Command RESPONSE: READ OK - J");
            verifyMessage("Command RESPONSE: DELETE OK - J");

        });


        tServer.stop();
    }


}
