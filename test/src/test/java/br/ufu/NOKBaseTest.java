package br.ufu;

import br.ufu.handler.ClientCommandHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class NOKBaseTest {

    @Mock
    private Appender mockAppender;

    private List<LogEvent> logEvents = new ArrayList<>();

    private Logger logger;

    @Before
    public void setup() {
        // prepare the appender so Log4j likes it
        when(mockAppender.getName()).thenReturn("MockAppender");
        when(mockAppender.isStarted()).thenReturn(true);

        logger = (Logger) LogManager.getLogger(ClientCommandHandler.class);
        logger.addAppender(mockAppender);


        Mockito.doAnswer(invocation -> {
            logEvents.add(invocation.getArgument(0));
            return null;
        }).when(mockAppender).append(Mockito.any());

    }

    @After
    public void tearDown() {
        logger.removeAppender(mockAppender);
    }

    protected void verifyMessage(String message) {
        assertTrue(logEvents.stream().anyMatch(m -> m.getMessage().getFormattedMessage().equals(message)));
    }

}
