package br.com.thread;

import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import br.com.atomix.Atomix;
import br.com.context.Context;
import br.com.enums.Operation;
import br.com.operations.Creat;
import br.com.operations.Delete;
import br.com.operations.Read;
import br.com.proto.ContextProto.SubscribeResponse;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import io.grpc.stub.StreamObserver;

public class ExecutorThread implements Runnable {

    private Queue<String> logQueue;

    private Context context;

    private ServerSocket serverSocket;

    private Map<String, List<StreamObserver<SubscribeResponse>>> observers;

    private Boolean semaphore;

    private CopycatClient copycatClient;



    public ExecutorThread(
            ServerSocket serverSocket,
            Queue<String> logQueue,
            Context context,
            Map<String, List<StreamObserver<SubscribeResponse>>> observers,
            Boolean semaphore, CopycatClient copycatClient) {
        this.logQueue = logQueue;

        this.context = context;
        this.serverSocket = serverSocket;
        this.observers = observers;
        this.semaphore = semaphore;
        this.copycatClient = copycatClient;
    }

    @Override
    public void run() {

        while (true) {
            try {

                String instruction;
                synchronized (GrpcThread.f1) {
                    instruction = GrpcThread.f1.poll();
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
            copycatClient.submit(new Creat(new BigInteger(params.get(1)), params.get(2))).join();
        } else if (Operation.UPDATE.name().equals(params.get(0))) {
            copycatClient.submit(new Read(new BigInteger(params.get(1)), params.get(2))).join();

        } else if (Operation.RETURN.name().equals(params.get(0))) {
            copycatClient.submit(new Read(new BigInteger(params.get(1)), params.get(2))).join();
        } else {
            copycatClient.submit(new Delete(new BigInteger(params.get(1)), params.get(2))).join();
        }

        logQueue.add(instruction);

    }


}
