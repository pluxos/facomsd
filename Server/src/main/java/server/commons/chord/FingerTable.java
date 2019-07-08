package server.commons.chord;

import io.atomix.core.Atomix;
import io.atomix.protocols.raft.MultiRaftProtocol;
import io.atomix.protocols.raft.ReadConsistency;
import server.commons.utils.FileUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FingerTable {
    private int key;
    private volatile Map<Integer, ChodNode> map;
    private int m;
    private int range;

    public FingerTable() {}

    public FingerTable(Atomix cluster) {
        try {
            Properties configProperties = FileUtils.getConfigProperties();
            this.range = Integer.parseInt(configProperties.getProperty("chord.range")) + 1;
            this.m = Integer.parseInt(configProperties.getProperty("chord.m"));

            this.map = Collections.synchronizedMap(
                    cluster.<Integer, ChodNode>mapBuilder("finger-table")
                            .withProtocol(MultiRaftProtocol.builder()
                                    .withReadConsistency(ReadConsistency.LINEARIZABLE)
                                    .build())
                            .build()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, ChodNode> getMap() {
        return map;
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

    public ChodNode catchResponsibleNode(Integer searchKey) {
        AtomicReference<ChodNode> res = new AtomicReference<>(null);

        for (Map.Entry<Integer, ChodNode> entry : this.map.entrySet()) {
            ChodNode v = entry.getValue();
            if (v.getRange().contains(searchKey)) {
                res.set(v);
            }
        }

        if(res.get() == null) {
            this.map.forEach((key, value) -> {
                if(value.getKey() <= searchKey)
                    res.set(value);
            });
        }

        if(res.get() == null) {
            AtomicInteger flag = new AtomicInteger(32);
            this.map.forEach((key, value) -> {
                if(searchKey < value.getKey() && value.getKey() < flag.get())
                    flag.set(key);
            });

            res.set(this.map.get(flag.get()));
        }

        return res.get();
    }

    public int updateFT(ChodNode chodNode) {
        return this.addNode(chodNode);
    }

    public int updateFT(Map<Integer, ChodNode> ft) {
        int flag = -1;
        int nodeAnt = Chord.getChodNode().getKey();
        for (int i = 1; i <= ft.size(); i ++) {
            if(ft.containsKey(i)) {
                ChodNode chodNode = ft.get(i);
                if (Chord.getChodNode().getKey() != chodNode.getKey() && chodNode.getKey() != nodeAnt) {
                    flag = this.addNode(chodNode);
                    nodeAnt = chodNode.getKey();
                }
            }
        }
        return flag;
    }

    public void removeNode(ChodNode chodNode) {
        for (int i = 1; i <= this.m; i ++) {
            if(this.map.containsKey(i)) {
                ChodNode chodNode1 = this.map.get(i);
                if (chodNode1.getKey() == chodNode.getKey()) {
                    this.map.remove(i);
                }
            }
        }
    }

    private synchronized int addNode(ChodNode chodNode) {
        int flag = -1;
        for (int i = 1; i <= this.m; i++) {
            Integer sucessor = ChordUtils.successor(this.key, i);
            if(sucessor > this.range) {
                sucessor -= this.range;
            }

            if(chodNode.getRange().contains(sucessor)) {
                if(!this.map.containsKey(i)){
                    flag = 1;
                    this.map.put(i, chodNode);
                } else if(this.map.get(i).getKey() != chodNode.getKey() || !this.map.get(i).getRange().equals(chodNode.getRange())) {
                    flag = 1;
                    this.map.put(i, chodNode);
                }
            } else {
                if (this.map.get(i) != null && chodNode.getKey() == this.map.get(i).getKey()) {
                    flag = 1;
                    this.map.remove(i);
                }
            }
        }

        return flag;
    }
}
