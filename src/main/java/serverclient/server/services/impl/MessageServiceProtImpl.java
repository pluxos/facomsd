package serverclient.server.services.impl;

import io.grpc.stub.StreamObserver;
import serverclient.constants.StringsConstants;
import serverclient.model.Message;
import serverclient.model.MessageServiceProtGrpc;
import serverclient.server.repository.MessageRepository;
import serverclient.server.repository.impl.MessageRepositoryMemory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageServiceProtImpl extends MessageServiceProtGrpc.MessageServiceProtImplBase {

    private final static Logger LOGGER = Logger.getLogger(MessageServiceProtImpl.class.getName());

    private static Set<StreamObserver<Message>> observers = ConcurrentHashMap.newKeySet();

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
    public StreamObserver<Message> createMessage(final StreamObserver<Message> responseObserver) {
        return new StreamObserver<Message>() {
            @Override
            public void onNext(Message note) {
//                List<RouteNote> notes = getOrCreateNotes(note.getLocation());
//
//                // Respond with all previous notes at this location.
//                for (RouteNote prevNote : notes.toArray(new RouteNote[0])) {
//                    responseObserver.onNext(prevNote);
//                }
//
//                // Now add the new note to the list
//                notes.add(note);
            }

            @Override
            public void onError(Throwable t) {
                LOGGER.log(Level.WARNING, "routeChat cancelled");
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<Message> readMessage(final StreamObserver<Message> responseObserver) {
        return new StreamObserver<Message>() {
            @Override
            public void onNext(Message note) {
//                List<RouteNote> notes = getOrCreateNotes(note.getLocation());
//
//                // Respond with all previous notes at this location.
//                for (RouteNote prevNote : notes.toArray(new RouteNote[0])) {
//                    responseObserver.onNext(prevNote);
//                }
//
//                // Now add the new note to the list
//                notes.add(note);
            }

            @Override
            public void onError(Throwable t) {
                LOGGER.log(Level.WARNING, "routeChat cancelled");
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<Message> updateMessage(final StreamObserver<Message> responseObserver) {
        return new StreamObserver<Message>() {
            @Override
            public void onNext(Message note) {
//                List<RouteNote> notes = getOrCreateNotes(note.getLocation());
//
//                // Respond with all previous notes at this location.
//                for (RouteNote prevNote : notes.toArray(new RouteNote[0])) {
//                    responseObserver.onNext(prevNote);
//                }
//
//                // Now add the new note to the list
//                notes.add(note);
            }

            @Override
            public void onError(Throwable t) {
                LOGGER.log(Level.WARNING, "routeChat cancelled");
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<Message> deleteMessage(final StreamObserver<Message> responseObserver) {
        return new StreamObserver<Message>() {
            @Override
            public void onNext(Message note) {
//                List<RouteNote> notes = getOrCreateNotes(note.getLocation());
//
//                // Respond with all previous notes at this location.
//                for (RouteNote prevNote : notes.toArray(new RouteNote[0])) {
//                    responseObserver.onNext(prevNote);
//                }
//
//                // Now add the new note to the list
//                notes.add(note);
            }

            @Override
            public void onError(Throwable t) {
                LOGGER.log(Level.WARNING, "routeChat cancelled");
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
