package server.commons.chord;

import server.commons.utils.FileUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FingerTable {
    private int key;
    private volatile Map<Integer, Node> ft;
    private int m;
    private int range;

    public FingerTable() {
        try {
            Properties configProperties = FileUtils.getConfigProperties();
            this.range = Integer.parseInt(configProperties.getProperty("chord.range")) + 1;
            this.m = Integer.parseInt(configProperties.getProperty("chord.m"));

            this.ft = Collections.synchronizedMap(new HashMap<>(this.m));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Node> getMap() {
        return ft;
    }

    public int getRange() {
        return this.range;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Integer getKey() {
        return this.key;
    }

    public Node catchResponsibleNode(Integer searchKey) {
        AtomicReference<Node> res = new AtomicReference<>(null);

        for (Map.Entry<Integer, Node> entry : this.ft.entrySet()) {
            Node v = entry.getValue();
            if (v.getRange().contains(searchKey)) {
                res.set(v);
            }
        }

        if(res.get() == null) {
            this.ft.forEach((key, value) -> {
                if(value.getKey() <= searchKey)
                    res.set(value);
            });
        }

        if(res.get() == null) {
            AtomicInteger flag = new AtomicInteger(32);
            this.ft.forEach((key, value) -> {
                if(searchKey < value.getKey() && value.getKey() < flag.get())
                    flag.set(key);
            });

            res.set(this.ft.get(flag.get()));
        }

        return res.get();
    }

    public int updateFT(Node node) {
        return this.addNode(node);
    }

    public void updateFT(Map<Integer, Node> ft) {
        for (Map.Entry<Integer, Node> entry : ft.entrySet()) {
            Node node = entry.getValue();
            if (Chord.getNode().getKey() != node.getKey())
                this.addNode(node);
        }
    }

    private synchronized int addNode(Node node) {
        int flag = -1;
        for (int i = 1; i <= this.m; i++) {
            Integer sucessor = ChordUtils.successor(this.key, i);
            if(sucessor > this.range) {
                sucessor -= this.range;
            }

            if(node.getRange().contains(sucessor)) {
                if(!this.ft.containsKey(i)){
                    flag = 1;
                    this.ft.put(i, node);
                } else if(this.ft.get(i).getKey() != node.getKey() || this.ft.get(i).getRange() != node.getRange()) {
                    flag = 1;
                    this.ft.put(i, node);
                }
            } else {
                if (this.ft.get(i) != null && node.getKey() == this.ft.get(i).getKey()) {
                    flag = 1;
                    this.ft.remove(i);
                }
            }
        }

        return flag;
    }
}
