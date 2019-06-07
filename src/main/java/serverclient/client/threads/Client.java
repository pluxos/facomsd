package serverclient.client.threads;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import serverclient.client.view.TerminalView;
import serverclient.model.Message;
import serverclient.model.MessageServiceProtGrpc;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final ManagedChannel channel;
    private MessageServiceProtGrpc.MessageServiceProtStub stub;

    private final TerminalView terminalView;

    public Client(String hostname, int port) {
        channel = ManagedChannelBuilder.forAddress(hostname, port)
                .build();
        stub = MessageServiceProtGrpc.newStub(channel);
        terminalView = new TerminalView();
    }

    public void shutdown() {
        logger.info("Shutting down ... ");
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            channel.shutdownNow();
        }
    }

    public Message sendRequisitionReceiveAnswer(Message msgRequest) {

        Timer timer = null;
        Message answer = null;

//        StreamObserver<Message> requestStreamObserver = stub.weChat(new StreamObserver<Message>() {
//            @Override
//            public void onNext(Message value) {
//                System.out.println("server  send:"+value.getText());
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                t.printStackTrace();
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("server onCompleted!!!");
//            }
//        });

        try {

            final int FIVE_SECONDS = 5000;

            timer = new Timer(FIVE_SECONDS, event -> {
                this.shutdown();
            });

            timer.start();

            if (msgRequest.getLastOption() == 1) {
                this.createMessage(msgRequest);
            } else if (msgRequest.getLastOption() == 2) {
                this.readMessage(msgRequest);
            } else if (msgRequest.getLastOption() == 3) {
                this.updateMessage(msgRequest);
            } else if (msgRequest.getLastOption() == 4) {
                this.deleteMessage(msgRequest);
            } else if (msgRequest.getLastOption() == 5) {
                timer.stop();
                this.shutdown();
            }

            timer.stop();

        } catch (Exception e) {
            logger.log(Level.WARNING, "Request to grpc server failed", e);
            this.shutdown();
        }

        return answer;
    }

    public void createMessage(Message msgRequest) throws InterruptedException {
        //info("*** RecordRoute");
        final CountDownLatch finishLatch = new CountDownLatch(1);

        //Observer da resposta do server
        StreamObserver<Message> responseObserver = new StreamObserver<Message>() {
            @Override
            public void onNext(Message msgResult) {
                System.out.println(msgResult.getText());
            }

            @Override
            public void onError(Throwable t) {
                //warning("RecordRoute Failed: {0}", Status.fromThrowable(t));
//                if (testHelper != null) {
//                    testHelper.onRpcError(t);
//                }
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                //info("Finished RecordRoute");
                finishLatch.countDown();
            }
        };

        //Observer da requisição ao server
        StreamObserver<Message> requestObserver = stub.createMessage(responseObserver);
//        try {
//            // Send numPoints points randomly selected from the features list.
//            for (int i = 0; i < numPoints; ++i) {
//                int index = random.nextInt(features.size());
//                Point point = features.get(index).getLocation();
//                info("Visiting point {0}, {1}", RouteGuideUtil.getLatitude(point),
//                        RouteGuideUtil.getLongitude(point));
//                requestObserver.onNext(point);
//                // Sleep for a bit before sending the next one.
//                Thread.sleep(random.nextInt(1000) + 500);
//                if (finishLatch.getCount() == 0) {
//                    // RPC completed or errored before we finished sending.
//                    // Sending further requests won't error, but they will just be thrown away.
//                    return;
//                }
//            }
//        } catch (RuntimeException e) {
//            // Cancel RPC
//            requestObserver.onError(e);
//            throw e;
//        }

        requestObserver.onNext(msgRequest);

        // Mark the end of requests
        requestObserver.onCompleted();

        // Receiving happens asynchronously
//        if (!finishLatch.await(1, TimeUnit.MINUTES)) {
//            warning("recordRoute can not finish within 1 minutes");
//        }
    }


    public TerminalView getTerminalView() {
        return terminalView;
    }
}