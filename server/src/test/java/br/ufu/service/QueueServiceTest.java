package br.ufu.service;

import br.ufu.model.Command;
import br.ufu.util.Constants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueueServiceTest {

    @Test
    public void shouldProduceAndConsumeF1() throws InterruptedException {
        QueueService queueService = new QueueService();
        String item = "F1-ITEM";
        Command command = new Command(item, null);
        queueService.produceF1(command);
        assertEquals(command, queueService.consumeF1());
    }

    @Test
    public void shouldProduceAndConsumeF2() throws InterruptedException {
        QueueService queueService = new QueueService();
        String item = "F2-ITEM";
        Command command = new Command(item, null);
        queueService.produceF2(command);
        assertEquals(command, queueService.consumeF2());
    }

    @Test
    public void shouldProduceAndConsumeF3() throws InterruptedException {
        QueueService queueService = new QueueService();
        String item = "F3-ITEM";
        Command command = new Command(item, null);
        queueService.produceF3(command);
        assertEquals(command, queueService.consumeF3());
    }

    @Test
    public void shouldResizeF1AfterLimit() {
        QueueService queueService = new QueueService();
        for (int i = 0; i < Constants.MAX_QUEUE_ITEMS + 5; i++) {
            Command command = new Command("ITEM-" + i, null);
            queueService.produceF1(command);
        }
    }

    @Test
    public void shouldResizeF2AfterLimit() {
        QueueService queueService = new QueueService();
        for (int i = 0; i < Constants.MAX_QUEUE_ITEMS + 5; i++) {
            Command command = new Command("ITEM-" + i, null);
            queueService.produceF2(command);
        }
    }

    @Test
    public void shouldResizeF3AfterLimit() {
        QueueService queueService = new QueueService();
        for (int i = 0; i < Constants.MAX_QUEUE_ITEMS + 5; i++) {
            Command command = new Command("ITEM-" + i, null);
            queueService.produceF3(command);
        }
    }

}
