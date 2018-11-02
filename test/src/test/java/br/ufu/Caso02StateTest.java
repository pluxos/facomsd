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
//import static br.ufu.TestUtil.getArgs;
//import static br.ufu.TestUtil.getThread;
//import static org.awaitility.Awaitility.await;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class Caso02StateTest extends BaseTest {
//    @Test
//    public void shouldTestRecover() throws Exception {
//
//        //Dado: Criei as variáveis
//        File tempLogFile = File.createTempFile("test_state_", ".log");
//        String[] commands = getArgs(tempLogFile, 4468);
//
//        Server serverSpy = Mockito.spy(new Server(commands));
//        Client clientSpy = Mockito.spy(new Client(commands));
//
//        Scanner mockScanner = Mockito.mock(Scanner.class);
//
//        List<String> inputs = new ArrayList<>();
//        for (int i = 1; i < 6; i++) {
//            inputs.add(String.format("CREATE %s I%s", i, i));
//        }
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
//            tClient.join();
//        });
//
//
//        tServer.stop();
//        //Restart server
//        commands = getArgs(tempLogFile, 4469);
//
//        Server newServerSpy = Mockito.spy(new Server(commands));
//        Client newClientSpy = Mockito.spy(new Client(commands));
//
//        when(newClientSpy.getScanner()).thenReturn(mockScanner);
//
//        //Start das Threads
//        Thread newTServer = getThread(newServerSpy);
//        newTServer.start();
//
//        inputs.clear();
//        currentInput[0] = 0;
//
//        for (int i = 1; i < 6; i++) {
//            inputs.add(String.format("READ %s", i));
//        }
//        inputs.add("sair");
//
//        await().dontCatchUncaughtExceptions().untilAsserted(() -> {
//            Thread tClient = getThread(newClientSpy);
//            tClient.start();
//            tClient.join();
//            verifyMessage("Command RESPONSE: READ OK - I1");
//            verifyMessage("Command RESPONSE: READ OK - I2");
//            verifyMessage("Command RESPONSE: READ OK - I3");
//            verifyMessage("Command RESPONSE: READ OK - I4");
//            verifyMessage("Command RESPONSE: READ OK - I5");
//        });
//
//        tServer.stop();
//    }
//}
