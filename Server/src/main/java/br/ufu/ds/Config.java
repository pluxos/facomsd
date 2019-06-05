package br.ufu.ds;

import java.io.*;
import java.util.Properties;

public class Config {

    public final Properties props;
    private static Config instance = null;

    public static Config getInstance() {
        if (instance == null) {
            synchronized (Config.class) {
                instance = new Config();
            }
        }
        return instance;
    }

    private Config() {
        props = new Properties();

        File f = new File("server.config");
        InputStream in = null;
        try {
            in = f.exists() ? new FileInputStream(f) :
                    Main.class.getClassLoader().getResourceAsStream("server.config");
            props.load(in);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPort() {
        try {
            return Integer.parseInt(props.getProperty("server.host.port"));
        } catch (Exception ex) {
            throw new RuntimeException("server.host.port not found!");
        }
    }

    public String getHostname() {
        try {
            return props.getProperty("server.host.name");
        } catch (Exception ex) {
            throw new RuntimeException("server.host.name not found!");
        }
    }

    public int getSnapshotInterval() {
        try {
            return Integer.parseInt(props.getProperty("server.log.time"));
        } catch (Exception ex) {
            throw new RuntimeException("server.log.time not found!");
        }
    }

    public int getLogFactor() {
        try {
            int factor = Integer.parseInt(props.getProperty("server.log.factor"));
            if (factor < 1) {
                throw new RuntimeException("server.log.factor should be greater than 1");
            }
            return Integer.parseInt(props.getProperty("server.log.factor"));
        } catch (Exception ex) {
            throw new RuntimeException("server.log.factor not found!");
        }
    }
}
