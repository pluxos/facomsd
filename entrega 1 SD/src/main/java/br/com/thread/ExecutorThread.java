package br.com.thread;

import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import br.com.context.Context;
import br.com.enums.Operation;
import br.com.proto.ContextProto.SubscribeResponse;
import io.grpc.stub.StreamObserver;

public class ExecutorThread implements Runnable {

    private Queue<String> logQueue;

    private Context context;

    private ServerSocket serverSocket;

    private Map<String, List<StreamObserver<SubscribeResponse>>> observers;

    private Boolean semaphore;

    public ExecutorThread(
            ServerSocket serverSocket,
            Queue<String> logQueue,
            Context context,
            Map<String, List<StreamObserver<SubscribeResponse>>> observers,
            Boolean semaphore) {
        this.logQueue = logQueue;

        this.context = context;
        this.serverSocket = serverSocket;
        this.observers = observers;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {

        while (true) {
            try {

                String instruction;
                synchronized (GrpcThread.f3) {
                    instruction = GrpcThread.f3.poll();
                }
                if (instruction != null && !semaphore) {
                    System.out.println("Executando instrucao: " + instruction);
                    execute(instruction);
                }
                Thread.sleep(1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * Executa a instrucao
     *
     * @param instruction
     */
    private void execute(String instruction) {
        List<String> params = Arrays.asList(instruction.split(";"));
        if (Operation.INSERT.name().equals(params.get(0))) {
            context.put(new BigInteger(params.get(1)), params.get(2));

        } else if (Operation.UPDATE.name().equals(params.get(0))) {
            if (!"Chave nao encontrada no contexto.".equals(context.get(new BigInteger(params.get(1))))) {
                context.put(new BigInteger(params.get(1)), params.get(2));

            }
        } else {
            context.remove(new BigInteger(params.get(1)));

        }

        logQueue.add(instruction);

    }



}
