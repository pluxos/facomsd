package application.Cliente;

import application.configuration.ApplicationProperties;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Properties;

public class Client {
    static byte[] addr = {127, 0, 0, 1};
    static String port;
    static String portClient;


    public static void main(String args[]) throws Exception {
        try {
            ApplicationProperties applicationProperties = new ApplicationProperties();
            Properties prop = applicationProperties.getProp();
            port = prop.getProperty("site.port");
            portClient = prop.getProperty("client.port");

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
        menuThread.setUsuario("manual");
        menuThread.start();

    }

}