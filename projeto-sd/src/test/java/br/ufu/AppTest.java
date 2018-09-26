package br.ufu;

import br.ufu.tcp.client.Client;
import br.ufu.tcp.server.ListeningClient;
import br.ufu.tcp.server.Server;
import org.junit.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Pattern;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    private CountCheck counter = new CountCheck();

    private String message1;
    private String message2;
    private String message3;
    private String message4;
    private String message5;

    @Before
    public void initialCount() {
        counter.initialize(2);
    }

    @Test
    public void Teste1() {

//        Thread thread1 = new Thread(() -> {
//            try {
//                Server.startServer();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//
//        Thread thread2 = new Thread(() -> {
//            Client client = new Client();
//            try {
//                client.setConsole(false);
//                client.setOption("1");
//                client.setMessage("Ola");
//                client.startConnection();
//
////                String regex = "Mensagem criada com sucesso! -[0-9]+-" + client.getMessage();
////                Assert.assertTrue(Pattern.matches(regex, client.getListeningThread().getMessage()));
////                counter.addOne();
//
//                message1 = client.getListeningThread().getMessage();
//
////                String[] words = client.getListeningThread().getMessage().split("-", 3);
//
//                client.stopConnection();
//
//
//                client.setId("1");
//                client.carregarMenu("2");
//                message2 = client.getListeningThread().getMessage();
//
//                client.carregarMenu("3");
//                client.setId("1");
//                client.setMessage("Ola2");
//                message3 = client.getListeningThread().getMessage();
//
//                client.setId("1");
//                client.carregarMenu("2");
//                message4 = client.getListeningThread().getMessage();
//
//                client.setId("1");
//                client.carregarMenu("4");
//                message5 = client.getListeningThread().getMessage();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });

//        Assert.assertEquals("Mensagem criada com sucesso!", message1);
//        Assert.assertEquals("Ola", message2);
//        Assert.assertEquals("Mensagem atualizada com sucesso!", message3);
//        Assert.assertEquals("Ola2", message4);
//        Assert.assertEquals("Mensagem deletada com sucesso!", message5);

    }
}
