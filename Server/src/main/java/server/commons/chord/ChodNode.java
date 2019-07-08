package server.commons.chord;

import server.commons.atomix.ClusterAtomix;
import server.commons.utils.FileUtils;

import java.io.IOException;
import java.util.*;

public class ChodNode {
    private Integer key;
    private List<Integer> range;
    private String ip;
    private int port;

    public void setRange(List<Integer> distributedSet) {
        range = distributedSet;
    }

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

    public List<Integer> getRange() {
        return range;
    }

    public void setRangeWithArray(List<Integer> arr) {
        this.range = arr;
    }

    public void clearRange() {
        range.clear();
        ClusterAtomix.getRange().clear();
    }

    public void setRange(int low, int high) {
        for(int i = low; i < high; i ++){
            range.add(i);
        }
    }

    public List<Integer> updateRange(int low, int high){
        List<Integer> res = new ArrayList<>();
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
        System.out.println(Arrays.toString(this.range.toArray()));

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
