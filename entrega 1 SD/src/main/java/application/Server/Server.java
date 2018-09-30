package application.Server;

import application.Process;
import application.configuration.ApplicationProperties;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Properties;

public class Server {

    static String port;
    static String logFile;
    static LinkedList<Process> f1 = new LinkedList<>();
    static LinkedList<Process> f2 = new LinkedList<>();
    static LinkedList<Process> f3 = new LinkedList<>();
    static InputStream inputStream = null;
    static OutputStream outputStream = null;
    static  ServerSocket serverSocket;

    public static void main(String args[]) {


        StartUp();

    }

    public static void StartUp() {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        try {
            Properties prop = applicationProperties.getProp();
            port = prop.getProperty("site.port");
            logFile = prop.getProperty("log.file");
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        ProcessThread tprocess = new ProcessThread(logFile);
        tprocess.start();

        try {
            serverSocket = new ServerSocket(Integer.parseInt(port));
            System.out.println("Servidor conectado na porta: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LogThread logThread = new LogThread();
        logThread.setLogFile(logFile);
        logThread.start();

        SplitThread splitThread = new SplitThread();
        splitThread.start();

        while (true) {

            Socket clientSocket = null;
            try {
                if (!serverSocket.isClosed()) {
                    clientSocket = serverSocket.accept();
                    System.out.println("Cliente conectado");
                }
            } catch (IOException e) {
                System.out.println("Servidor caiu");
            }

            try {
                inputStream = clientSocket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                outputStream = clientSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            CommandThread commandThread = new CommandThread();
            commandThread.setClientSocket(clientSocket);
            commandThread.setInputStream(inputStream);
            commandThread.setOutputStream(outputStream);
            commandThread.start();

        }
    }

}
