package br.com.thread;

import br.com.configuration.SocketSetting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientRecieveThread implements Runnable {

    private ServerSocket serverSocket;

    private InetAddress serverAddr;

    private SocketSetting serverSettings;

    private ExecutorService executor = Executors.newCachedThreadPool();


    public ClientRecieveThread(ServerSocket socketServer, InetAddress serverAddr, SocketSetting serverSettings) {
        this.serverSocket = socketServer;
        this.serverAddr = serverAddr;
        this.serverSettings = serverSettings;
    }

    @Override
    public void run() {
        try {
            Socket clientSocket = new Socket(serverSocket.getInetAddress(), serverSettings.getPort());

            while (true) {
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
                        clientSocket.getInputStream()));

                String sentence = inFromServer.readLine();
                ;

                System.out.println(sentence);

                Thread.sleep(1);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
