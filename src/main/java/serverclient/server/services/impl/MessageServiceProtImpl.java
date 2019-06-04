package serverclient.server.services.impl;

import serverclient.constants.StringsConstants;
import serverclient.model.Message;
import serverclient.model.MessageServiceProtGrpc;
import serverclient.server.repository.MessageRepository;
import serverclient.server.repository.impl.MessageRepositoryMemory;

import java.util.logging.Logger;

public class MessageServiceProtImpl extends MessageServiceProtGrpc.MessageServiceProtImplBase {

    private final static Logger LOGGER = Logger.getLogger(MessageServiceProtImpl.class.getName());

    private MessageRepository messageRepository = new MessageRepositoryMemory();

    public synchronized Message processMessage(Message message) {

        LOGGER.info("Mensagem " + message + " será processada para o banco de dados.");

        Message serverAnswer = null;

        switch (message.getLastOption()) {
            case 1:
                if (message.getText() == null || message.getText().trim().isEmpty()) {
                    serverAnswer = Message.newBuilder()
                            .setId(message.getId())
                            .setLastOption(message.getLastOption())
                            .setText(StringsConstants.ERR_EMPTY_SAVE_MESSAGE.toString())
                            .build();
                }
                serverAnswer = this.createMessage(message.getMessage());
                break;
            case 2:
            case 3:
            case 4:
                serverAnswer = this.deleteMessage(messageData.getMessage());
                break;
            default:
                serverAnswer = Message.newBuilder()
                        .setId(message.getId())
                        .setLastOption(message.getLastOption())
                        .setText(StringsConstants.ERR_INVALID_OPTION.toString())
                        .build();
                break;
        }

        return serverAnswer;

//        if (messageData.getAnswerQueue() != null) {
//            try {
//                messageData.getAnswerQueue().put(serverAnswer);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void createMessage(serverclient.model.Message request,
                              io.grpc.stub.StreamObserver<serverclient.model.Message> responseObserver) {
        Message response = serverclient.model.Message.newBuilder()
    }

    @Override
    public void readMessage(serverclient.model.Message request,
                            io.grpc.stub.StreamObserver<serverclient.model.Message> responseObserver) {
        Message response = serverclient.model.Message.newBuilder()
    }

    @Override
    public void updateMessage(serverclient.model.Message request,
                              io.grpc.stub.StreamObserver<serverclient.model.Message> responseObserver) {
        Message response = serverclient.model.Message.newBuilder()
    }

    @Override
    public void deleteMessage(serverclient.model.Message request,
                              io.grpc.stub.StreamObserver<serverclient.model.Message> responseObserver) {
        Message response = serverclient.model.Message.newBuilder()
    }
}
