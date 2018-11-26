package br.com.thread;

public class SplitThread extends Thread {

    //thread consumindo comandos de F1 e colocando cópias
    //do comando em uma fila F2 e em outra fila F3
    @Override
    public void run() {
        while (true) {

            try {
                String command;
                synchronized (GrpcThread.f1) {
                    command = GrpcThread.f1.poll();
                }
                if (command != null) {

                    synchronized (GrpcThread.f2) {
                        GrpcThread.f2.add(command);
                    }

                    synchronized (GrpcThread.f3) {
                        GrpcThread.f3.add(command);
                    }

                }
            } catch (
                    Exception e) {
                e.printStackTrace();
            }
        }
    }
}