package br.ufu.tcp.client;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {

    private Socket clientSocket;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private Scanner scanner;
    private ListeningServer listeningThread;

    private String message = null;
    private String option = null;
    private String id = null;

    private boolean isConsole = true;

    public void startConnection() throws Exception {

        this.startVariables();

        this.carregarMenu(this.option);

    } // end startConnection

    public void send(String option, String id, String message)throws IOException {
        String messageToSend = null;

        if ((id.isEmpty() && message.isEmpty())) {
            System.out.println("Digite valores existentes!");
            return;
        } else if (id.isEmpty()) {
            messageToSend = "" + option + "," + message;
        } else if (message.isEmpty()) {
            messageToSend = "" + option + "," + id;
        } else {
            messageToSend = "" + option + "," + id + "," + message;
        }

        toServer.println(messageToSend);
    } // end send

    public void stopConnection() throws IOException{
        System.out.println("Até logo!");
        toServer.close();
        clientSocket.close();
        System.exit(0);
    } // end stopConnection

    public void startVariables() throws Exception{
        String ip = "localhost";
        int port = 8080;
        clientSocket = new Socket();
        InetAddress host = InetAddress.getByName(ip);
        SocketAddress socketAddress = new InetSocketAddress(host, port);
        int timeout = 2000;

        try {
            clientSocket.connect(socketAddress, timeout);
            if (clientSocket.isConnected()) {
                System.out.println("Conectado ao servidor!");
            }
            toServer = new PrintWriter(clientSocket.getOutputStream(), true);

        } catch (SocketTimeoutException e) {
            System.err.println("Tempo esgotado na tentativa de conectar ao servidor : " + ip + ":" + port);
        } catch (IOException e) {
            System.err.println("Incapaz de conectar no servidor : " + ip + ":" + port);
        }
    }

    public void carregarMenu(String option) throws Exception{

        if (isConsole) {
            message = null;
            option = null;
            id = null;
        }

        System.out.println("MENU CRUD PARA SALVAR MENSAGENS");
        System.out.println("-------------------------------");
        System.out.println("1 - Criar mensagem (Create)");
        System.out.println("2 - Ler mensagem (Read)");
        System.out.println("3 - Atualizar mensagem (Update)");
        System.out.println("4 - Deletar mensagem (Delete)");
        System.out.println("5 - Sair (Quit)\n");
        System.out.println("Escolha a opção: ");

        scanner = new Scanner(System.in);

        while (true) {
            try {
                if (option == null) {
                    option = scanner.nextLine();
                }

                if (option.equals(1) && option.equals(2) && option.equals(3) && option.equals(4) && option.equals(5)) {
                    throw new InputMismatchException();
                }

                break;
            } catch (InputMismatchException e) {
                System.err.println("Apenas um dos comandos!");
                continue;
            }
        }

        Outer:
        while (true) {
            switch (option) {
                case "1": System.out.print("Digite a mensagem a ser criada: ");
                    if (message == null) {
                        message = scanner.nextLine();
                    }
                    id = "";
                    System.out.println();
                    break;

                case "2": System.out.print("Digite o ID da mensagem a ser lida: ");
                    try {
                        if (id == null) {
                            id = scanner.nextLine();
                        }
                    } catch (InputMismatchException e) {
                        System.err.println("Digite apenas um número inteiro!");
                        continue Outer;
                    }
                    message = "";
                    System.out.println();
                    break;

                case "3": System.out.print("Digite o ID da mensagem a ser atualizada: ");
                    try {
                        if (id == null) {
                            id = scanner.nextLine();
                        }
                    } catch (InputMismatchException e) {
                        System.err.println("Digite apenas um número inteiro!");
                        continue Outer;
                    }
                    System.out.print("\nDigite a mensagem: ");
                    if (message == null) {
                        message = scanner.nextLine();
                    }
                    System.out.println();
                    break;

                case "4": System.out.print("Digite o ID da mensagem a ser deletada: ");
                    try {
                        id = scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.err.println("Digite apenas um número inteiro!");
                        continue Outer;
                    }
                    message = "";
                    System.out.println();
                    break;

                default:
                    //this.send(option, "", "");
                    this.stopConnection();
                    break;
            } // end switch

            break;
        }

        listeningThread = new ListeningServer(clientSocket, this);
        listeningThread.start();

        this.send(option, id, message);

        isConsole = true;
    }

    public static void startClient() {
        Client client = new Client();
        try {
            client.startConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        startClient();
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public PrintWriter getToServer() {
        return toServer;
    }

    public void setToServer(PrintWriter toServer) {
        this.toServer = toServer;
    }

    public BufferedReader getFromServer() {
        return fromServer;
    }

    public void setFromServer(BufferedReader fromServer) {
        this.fromServer = fromServer;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public ListeningServer getListeningThread() {
        return listeningThread;
    }

    public void setListeningThread(ListeningServer listeningThread) {
        this.listeningThread = listeningThread;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isConsole() {
        return isConsole;
    }

    public void setConsole(boolean console) {
        isConsole = console;
    }
} // end class

