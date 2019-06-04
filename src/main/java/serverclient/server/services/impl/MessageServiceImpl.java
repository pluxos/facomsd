package serverclient.server.services.impl;

import serverclient.model.MessageOld;
import serverclient.server.repository.MessageRepository;
import serverclient.server.repository.impl.MessageRepositoryMemory;
import serverclient.server.services.MessageService;
import serverclient.constants.StringsConstants;
import serverclient.server.threads.handlers.MessageData;

import java.util.logging.Logger;

@Deprecated
public class MessageServiceImpl implements MessageService {

    private final static Logger LOGGER = Logger.getLogger(MessageServiceImpl.class.getName());

    private MessageRepository messageRepository = new MessageRepositoryMemory();

    @Override
    public synchronized void processMessage(MessageData messageData) {

        LOGGER.info("Mensagem " + messageData.getMessage() + " será processada para o banco de dados.");

        MessageOld serverAnswer = null;

        switch (messageData.getMessage().getLastOption()) {
            case 1:
                serverAnswer = this.createMessage(messageData.getMessage());
                break;
            case 2:
                serverAnswer = this.readMessage(messageData.getMessage());
                break;
            case 3:
                serverAnswer = this.updateMessage(messageData.getMessage());
                break;
            case 4:
                serverAnswer = this.deleteMessage(messageData.getMessage());
                break;
            default:
                serverAnswer = new MessageOld(StringsConstants.ERR_INVALID_OPTION.toString());
                break;
        }

        if (messageData.getAnswerQueue() != null) {
            try {
                messageData.getAnswerQueue().put(serverAnswer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public MessageOld createMessage(MessageOld message) {
        if (message.getMessage() == null || message.getMessage().trim().isEmpty()) {
            return new MessageOld(1, StringsConstants.ERR_EMPTY_SAVE_MESSAGE.toString());
        }

        return this.messageRepository.create(message);
    }

    @Override
    public MessageOld readMessage(MessageOld message) {

        return this.messageRepository.read(message);
    }

    @Override
    public MessageOld updateMessage(MessageOld message) {

        if (message.getMessage() == null || message.getMessage().trim().isEmpty()) {
            return new MessageOld(3, StringsConstants.ERR_EMPTY_UPDATE_MESSAGE.toString());
        }

        return this.messageRepository.update(message);
    }

    @Override
    public MessageOld deleteMessage(MessageOld message) {

        return this.messageRepository.delete(message);
    }

}
