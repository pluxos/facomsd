package data;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ArquiveManipulation {

    static Writer writer = null;
    static private Map<BigInteger, byte[]> map = new HashMap<BigInteger, byte[]>();

    public ArquiveManipulation() {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("data.txt", true)));
        } catch (Exception e) {
            System.out.println("Falha ao abrir arquivo");
        }
    }

    public void write(String text) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream("data.txt", true)))) {
            writer.append(text+"\n");
        }

        System.out.println("Escrito com sucesso");
    }

    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        ArquiveManipulation arq1 = new ArquiveManipulation();
        arq1.write("daniel");
    }

}
