package br.ufu.listener;

import br.ufu.model.Command;
import br.ufu.exception.InvalidCommandException;
import br.ufu.exception.ListenerException;
import br.ufu.handler.ClientHandler;
import br.ufu.service.CrudService;
import br.ufu.service.QueueService;
import org.junit.Test;
import org.mockito.Mockito;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class F3ListenerTest {

    private static final String ITEM = "READ 1";

    @Test
    public void shouldListenToF3AndExecuteCommand() {

        //GIVEN
        QueueService queueService = new QueueService();
        CrudService crudService = Mockito.mock(CrudService.class);
        Command command = new Command(ITEM, Mockito.mock(ClientHandler.class));
        queueService.produceF3(command);

        //DO
        Thread t = new Thread(new F3Listener(queueService, crudService));
        t.start();


        //THEN
        await().untilAsserted(() -> {
            verify(crudService, times(1)).execute(ITEM);
        });

    }


    @Test(expected = ListenerException.class)
    public void shouldThrowListenerExceptionOnInvalidCommand() throws InvalidCommandException {

        //GIVEN
        QueueService queueService = new QueueService();
        CrudService crudService = Mockito.mock(CrudService.class);
        Command command = new Command(ITEM, Mockito.mock(ClientHandler.class));
        queueService.produceF3(command);

        when(crudService.execute(anyString())).thenThrow(InvalidCommandException.class);

        F3Listener f3Listener = new F3Listener(queueService, crudService);
        f3Listener.listen();

    }

}
