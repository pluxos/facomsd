package br.ufu.tcp.resources;

import br.ufu.tcp.dao.MessageDAO;

import java.util.LinkedList;
import java.util.Queue;

public class Fila {

    private static final Fila fila = new Fila();
    private static Queue<String[]> queue1 = new LinkedList<>();
    private static Queue<String[]> queue2 = new LinkedList<>();
    private static Queue<String[]> queue3 = new LinkedList<>();


    private Thread1 thread1 = new Thread1();
    private Thread2 thread2 = new Thread2();
    private Thread3 thread3 = new Thread3();

    private MessageDAO messageDAO;

    private Fila() {

    }

    public static Fila getInstance() {
        return fila;
    }

    public synchronized void addComandosFila1(String[] message, MessageDAO messageDAO) {
        this.queue1.add(message);
        this.messageDAO = messageDAO;
        thread1.run();
    }

    class Thread1 implements Runnable{

        @Override
        public void run() {
            String[] message = Fila.queue1.element();
            Fila.queue2.add(message);
            Fila.queue3.add(message);
            Fila.queue1.remove();
            thread2.run();
            thread3.run();
        }
    }

    class Thread2 implements Runnable{

        @Override
        public void run() {
            String[] message = Fila.queue2.element();
            Fila.queue2.remove();
            messageDAO.receiveMessage(message);
        }
    }

    class Thread3 implements Runnable{

        @Override
        public void run() {
            String[] message = Fila.queue3.element();
            Fila.queue3.remove();
            try {
                if (!message[0].equals("2")) {
                    SingletonDataLoader.getInstance().saveOperationFile(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
