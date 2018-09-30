package application.Cliente;

import application.configuration.ApplicationProperties;

import java.io.IOException;
import java.math.BigInteger;
import java.net.*;
import java.util.Properties;

public class TesteIIIUser2 {

    public static void main(String args[]) throws UnknownHostException {
        Test1();
    }
    static byte[] addr = {127, 0, 0, 1};
    static String teste1port;
    static String teste1portClient;
    public static void Test1() throws UnknownHostException {

        System.out.println("---Testes I - CRUD OK---");
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
        menuThread.setKey(new BigInteger("3"));
        menuThread.setMessage("i");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("2");
        menuThread.setKey(new BigInteger("3"));

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("3");
        menuThread.setKey(new BigInteger("3"));
        menuThread.setMessage("i novo");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("2");
        menuThread.setKey(new BigInteger("3"));

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("4");
        menuThread.setKey(new BigInteger("3"));


        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---Testes I - CRUD NOK---");

        menuThread.setCode("1");
        menuThread.setKey(new BigInteger("3"));
        menuThread.setMessage("i");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("1");
        menuThread.setKey(new BigInteger("3"));
        menuThread.setMessage("i");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("2");
        menuThread.setKey(new BigInteger("66666"));

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("3");
        menuThread.setKey(new BigInteger("12105"));
        menuThread.setMessage("i novo");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("1");
        menuThread.setKey(new BigInteger("12105"));
        menuThread.setMessage("j novo");

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("2");
        menuThread.setKey(new BigInteger("12105"));

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        menuThread.setCode("4");
        menuThread.setKey(new BigInteger("12105"));


        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
}
