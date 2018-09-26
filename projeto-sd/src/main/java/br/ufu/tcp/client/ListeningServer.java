package br.ufu.tcp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ListeningServer extends Thread {

    Client client = null;

    Socket clientListeningSocket = null;

    public boolean isOver = false;

    String message = null;

    BufferedReader in = null;

    public ListeningServer(Socket clientListeningSocket, Client client) {
        this.clientListeningSocket = clientListeningSocket;
        this.client = client;
    }

    public void run() {
        try {
            this.pegarMensagem();

            if (isOver) {
                in.close();
                this.client.stopConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pegarMensagem() throws Exception{
        in = new BufferedReader(new InputStreamReader(clientListeningSocket.getInputStream()));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (!in.ready()) {
                        isOver = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    timer.cancel();
                }
            }
        }, 5000);

        while ((message = in.readLine()) != null && !isOver) {
            timer.cancel();
            System.out.println(message);
            this.client.carregarMenu(null);
        }

    }

    public void isOver(boolean isOver) {
        this.isOver = isOver;
    }

    public boolean getIsOver() {
        return this.isOver;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Socket getClientListeningSocket() {
        return clientListeningSocket;
    }

    public void setClientListeningSocket(Socket clientListeningSocket) {
        this.clientListeningSocket = clientListeningSocket;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
