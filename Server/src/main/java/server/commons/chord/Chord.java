package server.commons.chord;

public class Chord {
    private volatile static FingerTable ft;
    private volatile static Node node;

    public static FingerTable getFt() {
        return ft;
    }

    public static Node getNode() {
        return node;
    }

    public synchronized static void setNode(Node node) {
        Chord.node = node;
    }

    public synchronized static void setFt(FingerTable ft) {
        Chord.ft = ft;
    }
}
