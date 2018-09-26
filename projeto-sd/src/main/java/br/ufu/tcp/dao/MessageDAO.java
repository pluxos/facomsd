package br.ufu.tcp.dao;

import br.ufu.tcp.resources.SingletonDataLoader;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MessageDAO {

    private PrintWriter toClient;

    private static final AtomicLong counter = new AtomicLong(0);

    public static long getNextNumber(){
        return counter.incrementAndGet();
    }

    public MessageDAO() {

    }

    public void receiveMessage(String[] message) {

        long id = 0L;

        switch (message[0]) {
            case "1": if (!message[1].isEmpty()) {
                          toClient.println(this.create(message[1]));
                      }
                      break;

            case "2": id = Long.parseLong(message[1]);
                      if (id != 0L) {
                          toClient.println(this.read(id));
                      }
                      break;

            case "3": id = Long.parseLong(message[1]);
                      if (id != 0L && !message[2].isEmpty()) {
                          toClient.println(this.update(id, message[2]));
                      }
                      break;

            case "4": id = Long.parseLong(message[1]);
                      if (id != 0L) {
                          toClient.println(this.delete(id));
                      }
                      break;

            case "5": break;
        }
    }

    public String create(String message) {

        BigInteger nextId = BigInteger.valueOf(MessageDAO.getNextNumber());
        try {
            SingletonDataLoader.getDatabase().put(nextId, message);
            //return "Mensagem criada com sucesso! -" + nextId + "-" + message;
            return "Mensagem criada com sucesso!";
        } catch (Exception e) {
            //return "Ocorreu um erro: " + e.getMessage();
            return "Ocorreu um erro.";
        }
    }

    public String update(long id, String message) {

        BigInteger bigId = BigInteger.valueOf(id);

        if (SingletonDataLoader.getDatabase().containsKey(bigId)) {
            try {
                SingletonDataLoader.getDatabase().put(bigId, message);
                //return "Mensagem atualizada com sucesso! -" + bigId + "-" + message;
                return "Mensagem atualizada com sucesso!";
            } catch (Exception e) {
                //return "Ocorreu um erro: " + e.getMessage();
                return "Ocorreu um erro.";
            }

        }

        else {
            //return "Não existe o Id que está tentando atualizar.";
            return "Ocorreu um erro.";
        }

    }

    public String delete(long id) {

        BigInteger bigId = BigInteger.valueOf(id);

        if (SingletonDataLoader.getDatabase().containsKey(bigId)) {

            try {
                SingletonDataLoader.getDatabase().remove(bigId);
                //return "Mensagem deletada com sucesso! -" + bigId;
                return "Mensagem deletada com sucesso!";
            } catch (Exception e) {
                //return "Ocorreu um erro: " + e.getMessage();
                return "Ocorreu um erro.";
            }

        } else {
            //return "Não existe o Id que está tentando atualizar.";
            return "Ocorreu um erro.";
        }

    }

    public String read(long id) {

        BigInteger bigId = BigInteger.valueOf(id);

        if (SingletonDataLoader.getDatabase().containsKey(bigId)) {
            return (SingletonDataLoader.getDatabase().get(bigId));
        } else {
            //return "Não existe o Id que está tentando atualizar.\n";
            return "Ocorreu um erro.";
        }

    }

    public void setPrintWriter(PrintWriter toClient) {
        this.toClient = toClient;
    }
}
