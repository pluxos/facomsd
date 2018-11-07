package br.ufu;

import br.ufu.client.ClientConnection;
import br.ufu.handler.ClientCommandHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class BaseTest {

    @Mock
    private Appender mockAppender;

    private List<String> logEvents = new CopyOnWriteArrayList<>();

    private Logger logger;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule().silent();


    @Before
    public void setup() {
        // prepare the appender so Log4j likes it
        when(mockAppender.getName()).thenReturn("MockAppender");
        when(mockAppender.isStarted()).thenReturn(true);

        logger = (Logger) LogManager.getLogger(ClientConnection.class);
        logger.addAppender(mockAppender);


        Mockito.doAnswer(invocation -> {
            logEvents.add(((LogEvent) invocation.getArgument(0)).getMessage().getFormattedMessage());
            return null;
        }).when(mockAppender).append(Mockito.any());

    }

    @After
    public void tearDown() {
        logger.removeAppender(mockAppender);
    }

    protected void verifyMessage(String message) {
        assertTrue(logEvents.stream().anyMatch(m -> m.equals(message)));
    }

    protected Integer getLogSize() {
        return logEvents.size();
    }

    protected Long getLogSizeWithoutReads() {
        return logEvents.stream().filter(e -> !e.contains("READ")).count();
    }

    protected String getLastLog() {
        return logEvents.get(logEvents.size() - 1);
    }

}
