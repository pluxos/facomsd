package server.commons.Chord;

import server.commons.utils.FileUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class FingerTable {
    private HashMap<Integer, Chord> ft;

    public FingerTable() {
        try {
            Properties configProperties = FileUtils.getConfigProperties();
            int range = Integer.parseInt(configProperties.getProperty("chord.range"));

            this.ft = new HashMap<>(range);
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
}
