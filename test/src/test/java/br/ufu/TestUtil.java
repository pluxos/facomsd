package br.ufu;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtil {

    private static final String LINE_BREAK = "\n";

    public static String splitCommads(String... commands) {
        return StringUtils.join(commands, LINE_BREAK) + LINE_BREAK;
    }

    public static String[] getServerArgs(ServerConfig server) {
        String[] s = new String[]{
                "--log.path=/tmp/sd-snaps/",
                "--server.host=127.0.0.1",
                "--server.port=" + server.getPort(),
                "--server.id=" + server.getId(),
                "--smaller.key=" + server.getSmallerKey(),
                "--snap.path=/tmp/sd-snaps/",
                "--snap.time=" + server.getSnapTime(),
                "--max.key=" + server.getMaxKey(),
                "--right.servers=" + getStringFromArray(server.getRightServers()),
                "--left.servers=" + getStringFromArray(server.getLeftServers()),
                "--cluster.addresses=" + getStringFromArray(server.getClusterAddresses()),
                "--cluster.id=" + server.getClusterId(),
                "--server.atomix.port=" + server.getAtomixPort()
        };
        System.out.println("--------------------------------------");
        for(String a : s) {
            System.out.println(a);
        }
        return s;
    }

    public static String[] getClientArgs(int port) {
        return new String[]{
                "--server.host=127.0.0.1",
                "--server.port=" + port,
        };
    }

    private static String getStringFromArray(List<Integer> array) {
        if (array.size() == 0) return " ";
        StringBuilder stringFromArray = new StringBuilder();
        for (int item : array) {
            stringFromArray.append(item).append(",");
        }
        stringFromArray.deleteCharAt(stringFromArray.length()-1);
        return stringFromArray.toString();
    }

    public static List<Thread> initServers(Integer m, Integer n, Integer initialPort, Integer snapTime,
                                           Integer numOfNodesPerCluster) {
        Integer port = initialPort;
        BigInteger initialId = new BigInteger("2").pow(m).subtract(BigInteger.ONE);
        BigInteger id = initialId;
        BigInteger band = new BigInteger("2").pow(m).divide(new BigInteger(n.toString()));

        List<ServerConfig> serverConfigs = new ArrayList<>();
        ServerConfig serverConfig;
        String maxId = initialId.toString();
        List<Integer> nodes, ports, nodesIn;
        BigInteger smallerKey;

        Map<Integer, List<Integer>> nodesInCluster = new HashMap<>();

        for (int i=1; i<=n; i++) {
            nodes = new ArrayList<>();
            ports = new ArrayList<>();

            smallerKey =  id.subtract(band).add(BigInteger.ONE);

            for (int j=1; j<=numOfNodesPerCluster; j++) {
                nodesIn = new ArrayList<>();
                nodesIn.addAll(nodes);
                serverConfig = new ServerConfig();

                serverConfig.setPort(port);
                serverConfig.setId(id.toString());
                serverConfig.setSnapTime(snapTime);
                serverConfig.setMaxKey(maxId);
                serverConfig.setClusterAddresses(nodesIn);
                serverConfig.setClusterId(i);
                serverConfig.setAtomixPort(port + 1000);

                if (i == n) {
                    serverConfig.setSmallerKey(BigInteger.ZERO.toString());
                } else {
                    serverConfig.setSmallerKey(smallerKey.toString());
                }

                serverConfigs.add(serverConfig);

                nodes.add(port + 1000);
                ports.add(port);
                port++;
            }
            nodesInCluster.put(i, ports);

            id = id.subtract(band);
        }

        printClusters(nodesInCluster);

        int interactor = 0;
        for (int i=1; i<=n; i++) {
            for (int j=1; j<=numOfNodesPerCluster; j++) {
                if (i == 1) {
                    serverConfigs.get(interactor).setLeftServers(nodesInCluster.get(i+1));
                    serverConfigs.get(interactor).setRightServers(nodesInCluster.get(n));
                } else if (i == n) {
                    serverConfigs.get(interactor).setLeftServers(nodesInCluster.get(1));
                    serverConfigs.get(interactor).setRightServers(nodesInCluster.get(i-1));
                } else {
                    serverConfigs.get(interactor).setLeftServers(nodesInCluster.get(i+1));
                    serverConfigs.get(interactor).setRightServers(nodesInCluster.get(i-1));
                }
                interactor++;
            }
        }

        List<Thread> servers = new ArrayList<>();

        for (ServerConfig serverconf : serverConfigs) {
            servers.add(getThread(Mockito.spy(new Server(getServerArgs(serverconf)))));
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

    private static void printClusters(Map<Integer, List<Integer>>  clusters) {
        for (Integer key : clusters.keySet()) {
            System.out.println("----------------------------------");
            System.out.println("Cluster: " + key);

            StringBuilder nodes = new StringBuilder();
            nodes.append("Nodes: ");
            List<Integer> list = clusters.get(key);
            for (Integer item: list) {
                nodes.append(item).append("   ");
            }

            System.out.println(nodes.toString());
        }

    }

}
