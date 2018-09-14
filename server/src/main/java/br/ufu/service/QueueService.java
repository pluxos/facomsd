package br.ufu.service;

import br.ufu.model.Command;
import br.ufu.util.Constants;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueService {

    private static final double MAX_PERCENT = 0.7;
    private static final double RESIZE_PERCENT = 1.5;
    private BlockingQueue<Command> f1;
    private BlockingQueue<Command> f2;
    private BlockingQueue<Command> f3;

    public QueueService() {
        this.f1 = new ArrayBlockingQueue<>(Constants.MAX_QUEUE_ITEMS);
        this.f2 = new ArrayBlockingQueue<>(Constants.MAX_QUEUE_ITEMS);
        this.f3 = new ArrayBlockingQueue<>(Constants.MAX_QUEUE_ITEMS);
    }

    public Command consumeF1() throws InterruptedException {
        return this.f1.take();
    }

    public Command consumeF2() throws InterruptedException {
        return this.f2.take();
    }

    public Command consumeF3() throws InterruptedException {
        return this.f3.take();
    }

    private boolean offer(BlockingQueue<Command> queue, Command item) {
        queue = doResizeIfNeeded(queue);
        return queue.offer(item);
    }

    private BlockingQueue<Command> doResizeIfNeeded(BlockingQueue<Command> queue) {
        int queueRemainCapacity = queue.remainingCapacity();
        int queueSize = queue.size();
        int queueRealSize = queueRemainCapacity + queueSize;
        int limit = (int) (queueRealSize * MAX_PERCENT); //
        int currentUse = queueRealSize - queue.remainingCapacity();
        if (currentUse > limit) {
            queue = new ArrayBlockingQueue<>((int) (queue.size() * RESIZE_PERCENT));
        }
        return queue;
    }

    public void produceF1(Command item) {
        offer(f1, item);
    }

    public void produceF2(Command item) {
        offer(f2, item);
    }

    public void produceF3(Command item) {
        offer(f3, item);
    }
}
