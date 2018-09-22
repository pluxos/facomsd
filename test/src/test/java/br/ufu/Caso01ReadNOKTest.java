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
public class Caso01ReadNOKTest extends NOKBaseTest {

    @Test
    public void shouldCreateItem() throws Exception {

        //Dado: Criei as variáveis
        File tempLogFile = File.createTempFile("test_", ".log");
        String[] commands = getArgs(tempLogFile, 4466);

        Server serverSpy = Mockito.spy(new Server(commands));
        Client clientSpy = Mockito.spy(new Client(commands));

        Scanner mockScanner = Mockito.mock(Scanner.class);

        List<String> inputs = new ArrayList<>();
        inputs.add("READ 1");
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

        ClientCommandHandler clientCommandHandler = Mockito.spy(new ClientCommandHandler(clientSpy.getScanner(), clientSpy.getSocketClient()));

        when(clientSpy.getClientCommandHandler())
                .thenReturn(clientCommandHandler);


        //Start das Threads
        Thread tServer = getThread(serverSpy);
        tServer.start();

        await().dontCatchUncaughtExceptions().untilAsserted(() -> {
            Thread tClient = getThread(clientSpy);
            tClient.start();

            tClient.join();

            //O Arquivo de Log deve ser escrito
            assertEquals("", readFileToString(tempLogFile, defaultCharset()));

            verifyMessage("Command RESPONSE: Invalid command  - NOK - ID inexistente na base");

        });

        tServer.stop();
    }


}
