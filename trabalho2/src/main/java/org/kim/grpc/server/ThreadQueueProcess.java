package org.kim.grpc.server;

import java.util.concurrent.BlockingQueue;

public class ThreadQueueProcess extends Thread {

    private static ThreadQueueProcess threadQueueProcess;
    private int port;
    private BlockingQueue<Command> F1;
    private BlockingQueue<Command> F2;
    private BlockingQueue<Command> F3;
    private BlockingQueue<Command> F4;

    public ThreadQueueProcess(int port, BlockingQueue<Command> f1, BlockingQueue<Command> f2, BlockingQueue<Command> f3, BlockingQueue<Command> f4) {
        this.port = port;
        F1 = f1;
        F2 = f2;
        F3 = f3;
        F4 = f4;
    }

    public ThreadQueueProcess() {}

    public static ThreadQueueProcess init() {
        if (threadQueueProcess == null) {
            threadQueueProcess = new ThreadQueueProcess();
            threadQueueProcess.start();
        }

        return threadQueueProcess;
    }

    @Override
    public void run() {
        System.out.println("Thread Queue Process started");
        while (true) {
            try {
                Command command = F1.take();
                if (command.getKey() >= 1 && command.getKey() < 200000) {
                    if (port == 8080) {
                        System.out.println("Queuing commands in queues F2 and F3 for port " + port);

                        F2.add(command);
                        F3.add(command);

                        executeCommands();
                    }

                    else {
                        System.out.println("Server at port " + port + " do not resolve this command. Queuing command in queue F4");

                        F4.add(command);
                    }
                }

                else if (command.getKey() >= 200000 && command.getKey() < 400000) {
                    if (port == 8081) {
                        System.out.println("Queuing commands in queues F2 and F3 for port " + port);

                        F2.add(command);
                        F3.add(command);

                        executeCommands();
                    }

                    else {
                        System.out.println("Server at port " + port + " do not resolve this command. Queuing command in queue F4");

                        F4.add(command);
                    }
                }

                else if (command.getKey() >= 400000 && command.getKey() < 600000) {
                    if (port == 8082) {
                        System.out.println("Queuing commands in queues F2 and F3 for port " + port);

                        F2.add(command);
                        F3.add(command);

                        executeCommands();
                    }

                    else {
                        System.out.println("Server at port " + port + " do not resolve this command. Queuing command in queue F4");

                        F4.add(command);
                    }
                }

                else if (command.getKey() >= 600000 && command.getKey() < 800000) {
                    if (port == 8083) {
                        System.out.println("Queuing commands in queues F2 and F3 for port " + port);

                        F2.add(command);
                        F3.add(command);

                        executeCommands();
                    }

                    else {
                        System.out.println("Server at port " + port + " do not resolve this command. Queuing command in queue F4");

                        F4.add(command);
                    }
                }

                else if (command.getKey() >= 800000 && command.getKey() <= 1000000) {
                    if (port == 8084) {
                        System.out.println("Queuing commands in queues F2 and F3 for port " + port);

                        F2.add(command);
                        F3.add(command);

                        executeCommands();
                    }

                    else {
                        System.out.println("Server at port " + port + " do not resolve this command. Queuing command in queue F4");

                        F4.add(command);
                    }
                }
            }
            catch (InterruptedException e) { System.out.println("Thread Queue Process Error: " + e.getMessage()); }
        }
    }

    private void executeCommands() {
        // Executa comandos da F3
        ThreadExecute threadExecute_f2f3 = new ThreadExecute(F3, ThreadMain.dataStorage);
        threadExecute_f2f3.start();

        // Se a fila F4 deste servidor tiver algo, é executado
        if (!F4.isEmpty()) {
            ThreadExecute threadExecute_f4 = new ThreadExecute(F4, ThreadMain.dataStorage);
            threadExecute_f4.start();
        }
    }
}
