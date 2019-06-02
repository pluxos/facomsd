package server.controller;

import java.util.ArrayList;

public class Chord {
    private static int key;
    private static ArrayList<Integer> range = new ArrayList<Integer>();
    private static String ip;
    private static int port;

    public static int getKey() {
        return key;
    }

    public static void setKey(int key) {
        Chord.key = key;
    }

    public static ArrayList<Integer> getRange() {
        return range;
    }

    public static void setRange(int low, int high) {
        for(int i = low; i < high; i ++){
            range.add(i);
        }
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        Chord.ip = ip;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Chord.port = port;
    }
}
