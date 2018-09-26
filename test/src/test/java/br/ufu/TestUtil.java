package br.ufu;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class TestUtil {

    private static final String LINE_BREAK = "\n";

    public static String splitCommads(String... commands) {
        return StringUtils.join(commands, LINE_BREAK) + LINE_BREAK;
    }

    public static String[] getArgs(File tempLogFile, int port) {
        return new String[]{
                "--log.path=" + tempLogFile.getAbsolutePath(),
                "--server.host=127.0.0.1",
                "--server.port=" + port
        };
    }

    public static Thread getThread(Client clientSpy) {
        return new Thread(() -> {
            try {
                clientSpy.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static Thread getThread(Server serverSpy) {
        return new Thread(() -> {
            try {
                serverSpy.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}
