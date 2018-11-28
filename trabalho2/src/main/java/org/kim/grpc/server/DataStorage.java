package org.kim.grpc.server;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {

    private Map<Long, String> data = new HashMap<>();

    public Map<Long, String> getData() {
        return data;
    }

    public void setData(Long key, String data) {
        this.data.put(key, data);
    }

    public void removeData(Long key) {
        this.data.remove(key);
    }
}
