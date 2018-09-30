package application.Cliente;

import application.Server.Server;
import application.configuration.ApplicationProperties;

import java.io.IOException;
import java.math.BigInteger;
import java.net.*;
import java.util.Properties;

public class TesteIIParte1 {


    static byte[] addr = {127, 0, 0, 1};
    static String teste1port;
    static String teste1portClient;

    public static void main(String args[]) throws UnknownHostException {
        TestII();
    }

    public static void TestII() throws UnknownHostException {
        System.out.println("---Teste II - Recuperação do estado---");
        try {
            ApplicationProperties applicationProperties = new ApplicationProperties();
            Properties prop = applicationProperties.getProp();
            teste1port = prop.getProperty("teste1site.port");
            teste1portClient = prop.getProperty("teste1client.port");

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

        InetAddress inetAddress = InetAddress.getByAddress(addr);

        SocketAddress socketAddress = new InetSocketAddress(inetAddress, Integer.parseInt(teste1port));
        Socket socket = new Socket();

        try {
            socket.connect(socketAddress);
        } catch (IOException e) {
            System.out
                    .println("Erro na conexão com o socket!");

        }

        ResponserThread responserThread = new ResponserThread();
        responserThread.setSocket(socket);
        responserThread.start();

        MenuThread menuThread = new MenuThread();
        menuThread.setSocket(socket);
        menuThread.setUsuario("automatico");

        menuThread.start();

        menuThread.setCode("1");
        menuThread.setKey(new BigInteger("111"));
        menuThread.setMessage("item 1");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("1");
        menuThread.setKey(new BigInteger("222"));
        menuThread.setMessage("item 2");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("1");
        menuThread.setKey(new BigInteger("333"));
        menuThread.setMessage("item 3");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("1");
        menuThread.setKey(new BigInteger("444"));
        menuThread.setMessage("item 4");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("1");
        menuThread.setKey(new BigInteger("555"));
        menuThread.setMessage("item 5");
        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

}
