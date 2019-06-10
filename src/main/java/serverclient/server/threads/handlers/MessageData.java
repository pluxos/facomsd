package serverclient.server.threads.handlers;

import serverclient.model.Message;

import java.util.concurrent.BlockingQueue;

public class MessageData {

    private Message message;
    private BlockingQueue<Message> answerQueue;

    public MessageData(Message message, BlockingQueue<Message> answerQueue) {
        this.message = message;
        this.answerQueue = answerQueue;
    }

    public synchronized Message getMessage() {
        return message;
    }

    public synchronized void setMessage(Message message) {
        this.message = message;
    }

    public synchronized BlockingQueue<Message> getAnswerQueue() {
        return answerQueue;
    }
}
