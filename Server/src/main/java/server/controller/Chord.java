package server.controller;

import java.util.ArrayList;

public class Chord {
    private int key;
    private ArrayList<Integer> range = new ArrayList<Integer>();
    private String ip;
    private int port;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public ArrayList<Integer> getRange() {
        return range;
    }

    public void setRangeWithArray(ArrayList<Integer> arr) {
        this.range = arr;
    }

    public void setRange(int low, int high) {
        for(int i = low; i < high; i ++){
            range.add(i);
        }
    }

    public ArrayList<Integer> updateRange(int low, int high){
        ArrayList<Integer> res = new ArrayList<>();
        for (Integer integer : this.range) {
            if(low < high) {
                if (integer > low && integer <= high) {
                    res.add(integer);
                }
            }
            else {
                if(integer <= high || integer > low)
                    res.add(integer);
            }
        }
        this.range.removeAll(res);

        return res;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
