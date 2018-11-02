//package br.ufu;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.stubbing.Answer;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//import static br.ufu.TestUtil.*;
//import static java.nio.charset.Charset.defaultCharset;
//import static org.apache.commons.io.FileUtils.readFileToString;
//import static org.awaitility.Awaitility.await;
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class Caso01CrudOKTest extends BaseTest {
//
//    @Test
//    public void shoulTestCrudOK() throws Exception {
//
//        //Dado: Criei as variáveis
//        File tempLogFile = File.createTempFile("test_", ".log");
//        String[] commands = getArgs(tempLogFile, 4467);
//
//        Server serverSpy = Mockito.spy(new Server(commands));
//        Client clientSpy = Mockito.spy(new Client(commands));
//
//        Scanner mockScanner = Mockito.mock(Scanner.class);
//
//        List<String> inputs = new ArrayList<>();
//        inputs.add("CREATE 1 I");
//        inputs.add("READ 1");
//        inputs.add("UPDATE 1 IU");
//        inputs.add("READ 1");
//        inputs.add("DELETE 1");
//        inputs.add("sair");
//
//        final int[] currentInput = {0};
//
//        //Mockei com spy para simular o input do usuario
//        //Também poderei usar estas classes depois
//        when(clientSpy.getScanner()).thenReturn(mockScanner);
//        when(mockScanner.hasNext()).thenReturn(true);
//        when(mockScanner.nextLine()).thenAnswer((Answer<String>) invocation -> inputs.get(currentInput[0]++));
//
//        //Start das Threads
//        Thread tServer = getThread(serverSpy);
//        tServer.start();
//
//        await().dontCatchUncaughtExceptions().untilAsserted(() -> {
//            Thread tClient = getThread(clientSpy);
//            tClient.start();
//
//            tClient.join();
//
////        O Arquivo de Log deve ser escrito
//            String logCommands = splitCommads(
//                    "CREATE 1 I",
//                    "UPDATE 1 IU",
//                    "DELETE 1");
//
//            assertEquals(logCommands, readFileToString(tempLogFile, defaultCharset()));
//
//            //As seguintes repostas deverão ser logadas
//            verifyMessage("Command RESPONSE: CREATE OK - I");
//            verifyMessage("Command RESPONSE: READ OK - I");
//            verifyMessage("Command RESPONSE: UPDATE OK - IU");
//            verifyMessage("Command RESPONSE: READ OK - IU");
//            verifyMessage("Command RESPONSE: DELETE OK - IU");
//
//        });
//
//
//        tServer.stop();
//    }
//
//
//}
