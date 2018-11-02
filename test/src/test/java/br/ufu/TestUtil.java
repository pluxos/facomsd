package br.ufu;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.net.InetAddress;
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

    public static List<Server> initServers(Integer m, Integer n, Integer initialPort) {
        Integer port = initialPort;
        Integer lastPort = initialPort + n - 1;
        BigInteger initialId = new BigInteger("2").pow(m).subtract(new BigInteger("1"));
        BigInteger id = initialId;
        BigInteger band = new BigInteger("2").pow(m).divide(new BigInteger(n.toString()));
        System.out.println("id = " + id);
        System.out.println("band = " + band);

        List<Server> servers = new ArrayList<>();
        while(port <= lastPort){
            servers.add(new Server(getServerArgs(port, id.toString(), band.toString(), 10000,
                    port-1, port+1, initialId.toString())));
            port += 1;
        }
        return servers;
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
