package server.commons.chord;

public class Chord {
    private volatile static FingerTable ft;
    private volatile static ChodNode chodNode;

    public static FingerTable getFt() {
        return ft;
    }

    public static ChodNode getChodNode() {
        return chodNode;
    }

    public synchronized static void setChodNode(ChodNode chodNode) {
        Chord.chodNode = chodNode;
    }

    public synchronized static void setFt(FingerTable ft) {
        Chord.ft = ft;
    }
}
