package server.commons.Rows;

import server.commons.domain.GenericCommand;

import java.util.ArrayList;

public class RowF1 {
    private static volatile ArrayList<GenericCommand> f1 = new ArrayList<>();

    public synchronized static void addItem(GenericCommand item) {
        f1.add(item);
    }

    public synchronized static GenericCommand removeItem() {
        return f1.remove(0);
    }
}
