package server.commons.Chord;

public class Chord {
    private static FingerTable ft;
    private static Node node;

    public static FingerTable getFt() {
        return ft;
    }

    public static Node getNode() {
        return node;
    }

    public static void setNode(Node node) {
        Chord.node = node;
    }

    public static void setFt(FingerTable ft) {
        Chord.ft = ft;
    }
}
