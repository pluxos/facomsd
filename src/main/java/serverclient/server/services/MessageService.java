package serverclient.server.services;

import serverclient.model.MessageOld;
import serverclient.server.threads.handlers.MessageData;

public interface MessageService {
    void processMessage(MessageData messageData);
    MessageOld createMessage(MessageOld message);
    MessageOld readMessage(MessageOld message);
    MessageOld updateMessage(MessageOld message);
    MessageOld deleteMessage(MessageOld message);
}
