package br.ufu.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.servergreeting.GreeterGrpc;
import io.grpc.examples.servergreeting.Request;
import io.grpc.examples.servergreeting.RequestM;
import io.grpc.examples.servergreeting.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class ClientConnect {

    private static final Logger log = LogManager.getLogger(ClientConnect.class);
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;
    private final GreeterGrpc.GreeterStub asyncStub;

    public ClientConnect(String ip, int port) {
        this(ManagedChannelBuilder.forAddress(ip, port)
                .usePlaintext()
                .build());
    }

    ClientConnect(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = GreeterGrpc.newBlockingStub(channel);
        asyncStub = GreeterGrpc.newStub(channel);
    }

    public GreeterGrpc.GreeterBlockingStub getBlockingStub() {
        return blockingStub;
    }

    public GreeterGrpc.GreeterStub getAsyncStub() {
        return asyncStub;
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void greet(String message, String individual) {
        String[] msg = message.split(" ");
        System.out.println("Message: " + message);
        System.out.println("Individual: " + individual);




        Request request = Request.newBuilder().setAll(message).build();

        Response response;
        response = blockingStub.say(request);
        log.info("Response: " + response.getResp());

//        switch(msg[0]){
//            case "1":
//            case "2":
//            case "3":
//            case "4":{
//                Request request = Request.newBuilder().setAll(message).build();
//
//                Response response;
//                response = blockingStub.say(request);
//                log.info("Response: " + response.getResp());
//                break;
//            }
//            case "5":{
//                RequestM request = RequestM.newBuilder().setKey(msg[1]).setClient(individual).build();
//                Response response = blockingStub.monitor(request);
//                log.info("Change: " + response.getResp());
//                break;
//            }
//            default:
//                log.error("An error has ocurred, Greet default switch option has achieved!");
//        }

    }

}
