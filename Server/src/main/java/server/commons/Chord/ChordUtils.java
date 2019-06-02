package server.commons.Chord;

public class ChordUtils {
    public static Integer sucessor(Integer p, Integer i) {
        return (int) (p + Math.pow(2, (i - 1)));
    }
}
