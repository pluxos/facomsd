package br.ufu.listener;

import br.ufu.model.Command;
import br.ufu.exception.ListenerException;
import br.ufu.service.QueueService;
import org.junit.Test;
import org.mockito.Mockito;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

public class F1ListenerTest {

    private static final String ITEM = "ITEM";

    @Test
    public void shouldListenToF1AndProducesF2AndF3() {

        QueueService queueService = new QueueService();
        Command command = new Command(ITEM, null);
        queueService.produceF1(command);

        Thread t = new Thread(new F1Listener(queueService));
        t.start();

        await().untilAsserted(() -> {
            assertEquals(ITEM, queueService.consumeF2().getExecuteCommand());
            assertEquals(ITEM, queueService.consumeF3().getExecuteCommand());
        });

    }

    @Test(expected = ListenerException.class)
    public void shouldThrowListenerExceptionOnInterruptedException() throws InterruptedException {

        QueueService queueService = Mockito.mock(QueueService.class);

        Mockito.when(queueService.consumeF1()).thenThrow(InterruptedException.class);

        F1Listener f1Listener = new F1Listener(queueService);
        f1Listener.listen();

    }

}
