package br.ufu;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    private static final String LINE_BREAK = "\n";

    public static String splitCommads(String... commands) {
        return StringUtils.join(commands, LINE_BREAK) + LINE_BREAK;
    }

    public static String[] getServerArgs(int port, String id, String band, int snapTime,
                            int rightServer, int leftServer, String maxKey) {
        return new String[]{
                "--log.path=/tmp/sd-snaps/",
                "--server.host=127.0.0.1",
                "--server.port=" + port,
                "--server.id=" + id,
                "--server.band=" + band,
                "--snap.path=/tmp/sd-snaps/",
                "--snap.time=" + snapTime,
                "--right.server=" + rightServer,
                "--left.server=" + leftServer,
                "--max.key=" + maxKey
        };
    }

    public static String[] getClientArgs(int port) {
        return new String[]{
                "--server.host=127.0.0.1",
                "--server.port=" + port,
        };
    }

    public static List<Thread> initServers(Integer m, Integer n, Integer initialPort, Integer snapTime) {
        Integer port = initialPort;
        Integer lastPort = initialPort + n - 1;
        BigInteger initialId = new BigInteger("2").pow(m).subtract(new BigInteger("1"));
        BigInteger id = initialId;
        BigInteger band = new BigInteger("2").pow(m).divide(new BigInteger(n.toString()));

        List<Thread> servers = new ArrayList<>();

        Integer rightPort, leftPort;
        String serverId, serverBand;
        String maxId = initialId.toString();

        for (;port <= lastPort; port++, id = id.subtract(band)){
            serverId = id.toString();
            rightPort = port - 1;
            leftPort = port + 1;
            serverBand =  band.toString();

            if (port.equals(initialPort)) {
                rightPort = lastPort;
            }else if (port.equals(lastPort)) {
                leftPort = initialPort;
            }

            servers.add(getThread(Mockito.spy(new Server(
                    getServerArgs(port, serverId, serverBand, snapTime,rightPort, leftPort, maxId)))));
        }
        return servers;
    }

    public static void deleteLogsAndSnapshots() throws IOException {
        File directory = new File("/tmp/sd-snaps");
        if (directory .isDirectory()) {
            FileUtils.deleteDirectory(directory);
        }
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
