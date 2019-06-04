package serverclient.server.threads.handlers;

import serverclient.model.MessageOld;

import java.util.concurrent.BlockingQueue;

public class MessageData {

    private MessageOld message;
    private BlockingQueue<MessageOld> answerQueue;

    public MessageData(MessageOld message, BlockingQueue<MessageOld> answerQueue) {
        this.message = message;
        this.answerQueue = answerQueue;
    }

    public synchronized MessageOld getMessage() {
        return message;
    }

    public synchronized void setMessage(MessageOld message) {
        this.message = message;
    }

    public synchronized BlockingQueue<MessageOld> getAnswerQueue() {
        return answerQueue;
    }
}
