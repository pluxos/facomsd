package application.Cliente;

import application.configuration.ApplicationProperties;

import java.io.IOException;
import java.math.BigInteger;
import java.net.*;
import java.util.Properties;

public class TesteIIIUser1 {

    public static void main(String args[]) throws UnknownHostException {
        Test1();
    }
    static byte[] addr = {127, 0, 0, 1};
    static String port;
    static String portClient;
    public static void Test1() throws UnknownHostException {

        System.out.println("---Testes I - CRUD OK---");
        try {
            ApplicationProperties applicationProperties = new ApplicationProperties();
            Properties prop = applicationProperties.getProp();
            port = prop.getProperty("teste1site.port");
            portClient = prop.getProperty("teste1client.port");

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

        InetAddress inetAddress = InetAddress.getByAddress(addr);

        SocketAddress socketAddress = new InetSocketAddress(inetAddress, Integer.parseInt(port));
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
        menuThread.setKey(new BigInteger("2"));
        menuThread.setMessage("i");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("2");
        menuThread.setKey(new BigInteger("2"));

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("3");
        menuThread.setKey(new BigInteger("2"));
        menuThread.setMessage("i novo");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("2");
        menuThread.setKey(new BigInteger("2"));

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("4");
        menuThread.setKey(new BigInteger("2"));


        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---Testes I - CRUD NOK---");

        menuThread.setCode("1");
        menuThread.setKey(new BigInteger("2"));
        menuThread.setMessage("i");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("1");
        menuThread.setKey(new BigInteger("2"));
        menuThread.setMessage("i");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("2");
        menuThread.setKey(new BigInteger("55555"));

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("3");
        menuThread.setKey(new BigInteger("12104"));
        menuThread.setMessage("i novo");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("1");
        menuThread.setKey(new BigInteger("12104"));
        menuThread.setMessage("j novo");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("2");
        menuThread.setKey(new BigInteger("12104"));

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("4");
        menuThread.setKey(new BigInteger("12104"));


        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
}
