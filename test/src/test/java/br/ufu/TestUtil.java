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

    public static String[] getServerArgs(int port, String id, String smallerKey, int snapTime,
         int[] rightServers, int[] leftServers, String maxKey, int[] clusterAddresses, int clusterId) {
        return new String[]{
                "--log.path=/tmp/sd-snaps/",
                "--server.host=127.0.0.1",
                "--server.port=" + port,
                "--server.id=" + id,
                "--smaller.key=" + smallerKey,
                "--snap.path=/tmp/sd-snaps/",
                "--snap.time=" + snapTime,
                "--max.key=" + maxKey,
                "--right.servers=" + getStringFromArray(rightServers),
                "--left.servers=" + getStringFromArray(leftServers),
                "--cluster.addresses=" + getStringFromArray(clusterAddresses),
                "--cluster.id=" + clusterId
        };
    }

    public static String[] getClientArgs(int port) {
        return new String[]{
                "--server.host=127.0.0.1",
                "--server.port=" + port,
        };
    }

    private static String getStringFromArray(int[] array) {
        if (array.length == 0) return "";
        StringBuilder stringFromArray = new StringBuilder();
        for (int item : array) {
            stringFromArray.append(item).append(",");
        }
        return stringFromArray.toString();
    }

    public static List<Thread> initServers(Integer m, Integer n, Integer initialPort, Integer snapTime,
                                           Integer numOfNodesPerCluster) {
        Integer port = initialPort;
        Integer lastPort = (initialPort + n - 1) * numOfNodesPerCluster;
        BigInteger initialId = new BigInteger("2").pow(m).subtract(BigInteger.ONE);
        BigInteger id = initialId;
        BigInteger band = new BigInteger("2").pow(m).divide(new BigInteger(n.toString()));

        List<Thread> servers = new ArrayList<>();

        Integer rightPort, leftPort, aux, clusterId = 0;
        String serverId;
        String maxId = initialId.toString();
        BigInteger smallerKey;

        while (port <= lastPort) {
            aux = numOfNodesPerCluster;
            clusterId++;
            while(aux-- > 0) {

            }
            port++;
            id = id.subtract(band);
            serverId = id.toString();
            rightPort = port - 1;
            leftPort = port + 1;
            smallerKey =  id.subtract(band).add(BigInteger.ONE);

            if (port.equals(initialPort)) {
                rightPort = lastPort;
            }else if (port.equals(lastPort)) {
                leftPort = initialPort;
                smallerKey = BigInteger.ZERO;
            }

            servers.add(getThread(Mockito.spy(new Server(getServerArgs(
                    port, serverId, smallerKey.toString(), snapTime,rightPort, leftPort, maxId)))));
        }
        return servers;
    }

    public static void deleteLogsAndSnapshots() throws IOException {
        File directory = new File("/tmp/sd-snaps");
        if (directory .isDirectory()) {
            FileUtils.deleteDirectory(directory);
        }
    }

    public static void deleteAtomixLogs() throws IOException {
        File directory = new File("/tmp/atomix-logs");
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
