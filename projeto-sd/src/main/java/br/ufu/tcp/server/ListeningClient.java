package br.ufu.tcp.server;

import br.ufu.tcp.dao.MessageDAO;
import br.ufu.tcp.resources.Fila;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ListeningClient implements Runnable {

    private Socket clientSocket;
    private PrintWriter toClient;
    private BufferedReader fromClient;
    private MessageDAO messageDAO;

    public ListeningClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.messageDAO = new MessageDAO();
    }

    @Override
    public void run() {
        try {

            this.carregarStreams();

            this.capturarMensagem();

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void carregarStreams() throws Exception{

        toClient = new PrintWriter(clientSocket.getOutputStream(), true);

        messageDAO.setPrintWriter(toClient);

        System.out.println("PrintWriter está pronto.");

        fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("BufferedReader está pronto.");
    }

    public void capturarMensagem() throws Exception {

        String msgFromClient;

        while ((msgFromClient = fromClient.readLine()) != null) {
            System.out.println("Mensagem capturada! " + msgFromClient);

            String[] optionMessage = msgFromClient.split(",",2);
            String[] message = null;

            System.out.println("A opção escolhida " + optionMessage[0] + ".");
            if (optionMessage[0].equals("3")) {
                message = optionMessage[1].split(",",2);
                optionMessage = new String[]{optionMessage[0], message[0], message[1]};
                System.out.println("A mensagem foi: " + message[0] + " " + message[1]);
            } else {
                System.out.println("A mensagem foi: " + optionMessage[1]);
            }

            Fila.getInstance().addComandosFila1(optionMessage, messageDAO);
        }

    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public PrintWriter getToClient() {
        return toClient;
    }

    public void setToClient(PrintWriter toClient) {
        this.toClient = toClient;
    }

    public BufferedReader getFromClient() {
        return fromClient;
    }

    public void setFromClient(BufferedReader fromClient) {
        this.fromClient = fromClient;
    }

    public MessageDAO getMessageDAO() {
        return messageDAO;
    }

    public void setMessageDAO(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }
}
