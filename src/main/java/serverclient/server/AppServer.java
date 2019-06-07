package serverclient.server;

import serverclient.model.Message;
import serverclient.model.MessageOld;
import serverclient.server.chord.chordring.Node;
import serverclient.server.threads.handlers.MessageData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

import static serverclient.server.chord.chordring.ChordRing.fix_nodes;

public class AppServer {

    private static final Logger logger = Logger.getLogger(AppServer.class.getName());

    private static volatile BlockingQueue<MessageData> fila1;
    private static volatile BlockingQueue<MessageOld> fila2;
    private static volatile BlockingQueue<MessageData> fila3;
    private static volatile BlockingQueue<Message> fila4;

    private ExecutorService queueThreadPool = Executors.newFixedThreadPool(4);

    static {
        fila1 = new LinkedBlockingDeque<>();
        fila2 = new LinkedBlockingDeque<>();
        fila3 = new LinkedBlockingDeque<>();
        fila4 = new LinkedBlockingDeque<>();
    }

    public static volatile List<Node> nodelist = new ArrayList<Node>();

    public static void main(String[] args) throws Exception {
        logger.info("Server startup. Args = " + Arrays.toString(args));

        //System.out.println("Please enter the <number of desired nodes>: ");
        int initialNumberOfNodes = 10;//Integer.parseInt(input.readLine());
        //System.out.println("Please enter the <log of ring size>: ");
        int ringSize = 64;
        //Number of replicas
        int numberOfReplicas = 5;
        int acrescimoPort = 1;

        System.out.printf("Initial number of nodes: %d\n ring size: %d\n replication factor: %d\n\n",
                initialNumberOfNodes, ringSize, numberOfReplicas);

        // create initial ring
        for ( ; acrescimoPort <= initialNumberOfNodes; acrescimoPort++){
            Node n = new Node("localhost", Integer.toString(acrescimoPort), ringSize ,numberOfReplicas);
            nodelist.add(n);
        }

        fix_nodes(nodelist);

        for (Node n: nodelist){
            n.start();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e1) {
            System.out.println("Main couldn't sleep!");
        }
    }

}
