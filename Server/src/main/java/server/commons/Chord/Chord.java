package server.commons.Chord;

import server.commons.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

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

    public void setNewKey() {
        Properties properties;
        try {
            properties = FileUtils.getConfigProperties();

            int fim = Integer.parseInt(properties.getProperty("chord.range"));
            int val = new Random().nextInt(fim);

            this.setKey(val);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
