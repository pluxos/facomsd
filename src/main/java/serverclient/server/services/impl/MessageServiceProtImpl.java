package serverclient.server.services.impl;

import io.grpc.stub.StreamObserver;
import serverclient.constants.StringsConstants;
import serverclient.model.Message;
import serverclient.model.MessageOld;
import serverclient.model.MessageServiceProtGrpc;
import serverclient.server.repository.MessageRepository;
import serverclient.server.repository.impl.MessageRepositoryMemory;
import serverclient.server.threads.handlers.MessageData;
import serverclient.server.threads.messagequeues.firststage.FirstQueueThread;

import java.util.Set;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageServiceProtImpl extends MessageServiceProtGrpc.MessageServiceProtImplBase {

    private final static Logger LOGGER = Logger.getLogger(MessageServiceProtImpl.class.getName());

    private static Set<StreamObserver<Message>> observers = ConcurrentHashMap.newKeySet();

    private MessageRepository messageRepository = new MessageRepositoryMemory();

//    public synchronized Message processMessage(MessageData message, ExecutorService executor) {
//
//        LOGGER.info("Mensagem " + message + " será processada para o banco de dados.");
//
//        Message serverAnswer = null;
//
//        switch (message.getMessage().getLastOption()) {
//            case 1:
//                if (message.getMessage().getText() == null || message.getMessage().getText().trim().isEmpty()) {
//                    serverAnswer = Message.newBuilder()
//                            .setId(message.getMessage().getId())
//                            .setLastOption(message.getMessage().getLastOption())
//                            .setText(StringsConstants.ERR_EMPTY_SAVE_MESSAGE.toString())
//                            .build();
//                } else {
//                    serverAnswer =
//                }
//                break;
//            case 2:
//            case 3:
//            case 4:
//                serverAnswer = this.deleteMessage(messageData.getMessage());
//                break;
//            default:
//                serverAnswer = Message.newBuilder()
//                        .setId(message.getId())
//                        .setLastOption(message.getLastOption())
//                        .setText(StringsConstants.ERR_INVALID_OPTION.toString())
//                        .build();
//                break;
//        }
//
//        return serverAnswer;
//
////        if (messageData.getAnswerQueue() != null) {
////            try {
////                messageData.getAnswerQueue().put(serverAnswer);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////        }
//    }

    @Override
    public void createUpdateMessage(Message request, StreamObserver<Message> responseObserver) {

        BlockingQueue<Message> answerQueue = new LinkedBlockingDeque<>();
        Message serverAnswer = null;

        if (request.getText() == null || request.getText().trim().isEmpty()) {
            serverAnswer = Message.newBuilder()
                    .setId(request.getId())
                    .setLastOption(request.getLastOption())
                    .setText(StringsConstants.ERR_EMPTY_SAVE_MESSAGE.toString())
                    .build();


        } else {
            MessageData messageData = new MessageData(request, answerQueue);

            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(new FirstQueueThread(messageData));

            try {
                serverAnswer = answerQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        responseObserver.onNext(serverAnswer);
        responseObserver.onCompleted();
    }

    @Override
    public void readDeleteMessage(Message request, final StreamObserver<Message> responseObserver) {

        BlockingQueue<Message> answerQueue = new LinkedBlockingDeque<>();

        MessageData messageData = new MessageData(request, answerQueue);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new FirstQueueThread(messageData));

        //Message answer = Message.newBuilder().setLastOption(1).setId(1).setText("Test").build();
        Message answer = null;

        try {
            answer = answerQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        responseObserver.onNext(answer);
        responseObserver.onCompleted();
    }
}
