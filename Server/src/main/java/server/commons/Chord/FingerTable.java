package server.commons.Chord;

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
    private Map<Integer, Node> ft;
    private int m;
    private int range;

    public FingerTable() {
        try {
            Properties configProperties = FileUtils.getConfigProperties();
            this.range = Integer.parseInt(configProperties.getProperty("chord.range"));
            this.m = Integer.parseInt(configProperties.getProperty("chord.m"));

            this.ft = Collections.synchronizedMap(new HashMap<>(this.m));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Node> getFt() {
        return ft;
    }

    public void setFt(Map<Integer, Node> ft) {
        this.ft = ft;
    }

    public void addToFt(Integer key, Node node) {
        this.ft.put(key, node);
    }

    public Node getChord(Integer key) {
        return this.ft.get(key);
    }

    public int addNode(Node node) {
        int flag = -1;
        for (int i = 1; i <= this.m; i++) {
            Integer sucessor = ChordUtils.sucessor(this.key, i);
            if(sucessor > this.range) {
            	sucessor -= this.range;
            }

            if(node.getRange().contains(sucessor)) {
                flag = 1;
                this.ft.put(i, node);
            }
        }

        return flag;
    }

    public Node catchResponsibleNode(Integer searchKey) {
        AtomicReference<Node> res = new AtomicReference<>(null);
        for(int i = 1; i <= this.ft.size(); i ++) {
            if(this.ft.get(i).getRange().contains(searchKey)) {
                res.set(this.ft.get(i));
            }
        }

        if(res.get() == null) {
            this.ft.forEach((key, value) -> {
                if(key <= searchKey)
                    res.set(value);
            });
        }

        if(res.get() == null) {
            AtomicInteger flag = new AtomicInteger(32);
            this.ft.forEach((key, value) -> {
                if(searchKey < key && key < flag.get())
                    flag.set(key);
            });

            res.set(this.ft.get(flag.get()));
        }

        return res.get();
    }

    public void setKey(int key) {
        this.key = key;
    }
    public Integer getKey() {
        return this.key;
    }

    public void updateFT(Node node) {
        if(this.addNode(node) == 1) {
            ChordUtils.notifyUpdateFT(this.ft);
        }
    }

    public void updateFT(Map<Integer, Node> ft) {
        ft.forEach((key, node) -> {
            if(Chord.getNode().getKey() != node.getKey())
                if (this.addNode(node) == 1) {
                    ChordUtils.notifyUpdateFT(this.ft);
                }
        });
    }
}
