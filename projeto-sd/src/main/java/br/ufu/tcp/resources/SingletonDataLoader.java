package br.ufu.tcp.resources;

import br.ufu.tcp.dao.MessageDAO;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingletonDataLoader {

    private static SingletonDataLoader inst = new SingletonDataLoader();
    private static Map<BigInteger, String> dados;

    private Path currentFile = Paths.get("./bd.txt");
    private BufferedReader reader;
    private BufferedWriter writer;

    private static final String regex1 = "^\\(([0-9]+),([\\s\\S]+)\\)$";
    private static final Pattern pattern1 = Pattern.compile(regex1);

    private static final String regex2 = "^\\(([0-9]+),([0-9]+),([\\s\\S]+)\\)$";
    private static final Pattern pattern2 = Pattern.compile(regex2);

    private MessageDAO messageDAO;

    private SingletonDataLoader() {
        super();
    }

    public synchronized void readFile() {

        dados = new HashMap<>();

        try {

            String line = null;

            while ((line = reader.readLine()) != null) {
                Matcher matcher1 = pattern1.matcher(line);
                Matcher matcher2 = pattern2.matcher(line);
                if (matcher1.find() && !matcher1.group(1).equals("3")) {
                    if (matcher1.group(1).equals("1")) {
                        messageDAO.create(matcher1.group(2));
                    } else if (matcher1.group(1).equals("4")) {
                        messageDAO.delete(Long.parseLong(matcher1.group(2)));
                    }
                } else if (matcher2.find() && matcher2.group(1).equals("3")) {
                    messageDAO.update(Long.parseLong(matcher2.group(2)), matcher2.group(3));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            closeFile();
        }
    }

    public synchronized void saveOperationFile(String[] message) throws IOException {

        StringBuilder stringData = new StringBuilder();
        stringData.append("(");
        for (int i = 0; i < message.length; i++) {
            stringData.append(message[i]);
            if (i != (message.length - 1)) {
                stringData.append(",");
            } else {
                stringData.append(")");
            }
        }

        try {
            writer.write(stringData.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            closeFile();
        } finally {
            writer.flush();
        }
    }

    public synchronized void openFile() {

        try {
            if (Files.notExists(currentFile)) {
                Files.createFile(currentFile);
            }
            this.reader = Files.newBufferedReader(currentFile);
            this.writer = Files.newBufferedWriter(currentFile, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            closeFile();
        }

    }

    public synchronized void closeFile() {
        try {
            this.writer.flush();
            this.reader.close();
            this.writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SingletonDataLoader getInstance() {
        return inst;
    }

    public static Map<BigInteger, String> getDatabase() {
        return dados;
    }

    public void setMessageDAO(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }
}
