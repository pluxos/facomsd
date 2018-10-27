package br.ufu.listener;

import br.ufu.handler.ClientHandlerSocket;
import br.ufu.model.Command;
import br.ufu.service.CrudService;
import br.ufu.service.QueueService;
import org.junit.Test;
import org.mockito.Mockito;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class F3ListenerTest {

    private static final String ITEM = "READ 1";

    @Test
    public void shouldListenToF3AndExecuteCommand() {

        //GIVEN
//        QueueService queueService = new QueueService();
//        CrudService crudService = Mockito.mock(CrudService.class);
//        Command command = new Command(ITEM, Mockito.mock(ClientHandlerSocket.class));
//        queueService.produceF3(command);
//
//        //DO
//        Thread t = new Thread(new F3Listener(queueService, crudService));
//        t.start();
//
//
//        //THEN
//        await().untilAsserted(() -> {
//            verify(crudService, times(1)).execute(ITEM);
//        });

    }

}
