package application.Server;

import application.Process;

public class SplitThread extends Thread {

    //thread consumindo comandos de F1 e colocando cópias
    //do comando em uma fila F2 e em outra fila F3
    @Override
    public void run() {
        while (true) {

            try {
                Process process;
                synchronized (Server.f1) {
                    process = Server.f1.poll();
                }
                if (process != null) {

                    synchronized (Server.f2) {
                        Server.f2.add(process);
                    }

                    synchronized (Server.f3) {
                        Server.f3.add(process);
                    }

                }
            } catch (
                    Exception e) {
                e.printStackTrace();
            }
        }
    }
}
