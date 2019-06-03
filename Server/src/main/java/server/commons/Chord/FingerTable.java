package server.commons.Chord;

import server.commons.utils.FileUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FingerTable {
    private int key;
    private HashMap<Integer, Chord> ft;
    private int m;
    private int range;

    public FingerTable() {
        try {
            Properties configProperties = FileUtils.getConfigProperties();
            this.range = Integer.parseInt(configProperties.getProperty("chord.range"));
            this.m = Integer.parseInt(configProperties.getProperty("chord.m"));

            this.ft = new HashMap<>(this.m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer, Chord> getFt() {
        return ft;
    }

    public void setFt(HashMap<Integer, Chord> ft) {
        this.ft = ft;
    }

    public void addToFt(Integer key, Chord node) {
        this.ft.put(key, node);
    }

    public Chord getChord(Integer key) {
        return this.ft.get(key);
    }

    public void addNode(Chord node) {
        for (int i = 1; i <= this.m; i++) {
            Integer sucessor = ChordUtils.sucessor(this.key, i);
            if(sucessor > this.range) sucessor -= this.range;

            System.err.println("i: " + i + " suc: "+sucessor);

            if(node.getRange().contains(sucessor)) {
                this.ft.put(i, node);
            }
        }
    }

    public Chord catchResponsibleNode(Integer searchKey) {
        AtomicReference<Chord> res = new AtomicReference<>(null);
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
}
