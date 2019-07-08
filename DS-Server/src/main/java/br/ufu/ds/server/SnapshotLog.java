package br.ufu.ds.server;

import br.ufu.ds.Config;
import br.ufu.ds.grpc.ServerRpc;
import com.google.protobuf.ByteString;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

public class SnapshotLog implements Runnable{

    private int currentSnap = 1;
    private int timer;
    private boolean run = true;
    private final int snapFactor = Config.getInstance().getLogFactor();
    public final String currentDirectory = Paths.get("").toAbsolutePath().toString();

    private static final Logger logger = Logger.getLogger(ServerRpc.class.getName());
    private HashMap<BigInteger, ByteString> lastBdCopy = null;

    private static SnapshotLog instance;

    private SnapshotLog() {
        try {
            this.timer = Config.getInstance().getSnapshotInterval();
            this.timer *= 1000;

            // load current file number!
            // All files from current directory!

            Collection<File> files = FileUtils.listFiles(new File(currentDirectory), new String[]{"snap"}, false);
            files.forEach(file -> {
                try {
                    int snapNumber;
                    snapNumber = Integer.parseInt(file.getName().split("\\.")[1]);
                    if (snapNumber > currentSnap) {
                        currentSnap = snapNumber;
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Invalid snapshot file name!");
                }
            });

            // load current snapshot
            String fileName = String.format("snapshot.%s.snap", currentSnap);
            File fileSnap = new File(fileName);
            if (fileSnap.exists()) {
                ObjectInput objectInput = new ObjectInputStream(new FileInputStream(fileName));
                Database.getInstance().setDatabase((HashMap<BigInteger, ByteString>) objectInput.readObject());
            }
            // load latest log
            fileName = String.format("log.%d.bin", currentSnap - 1);
            File fileLog = new File(fileName);
            if (fileLog.exists()) {
                new DatabaseProducer(fileLog).run();
            }

            lastBdCopy = Database.getInstance().getDbCopy();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static SnapshotLog getInstance() {
        if (instance == null) {
            synchronized (SnapshotLog.class) {
                instance = new SnapshotLog();
            }
        }
        return instance;
    }

    public int getCurrentSnap() {
        return currentSnap;
    }

    public void snap() {
        HashMap<BigInteger, ByteString> db =
                Database.getInstance().getDb();

        if (lastBdCopy != null) {
            if (db.hashCode() == lastBdCopy.hashCode()) {
                return;
            } else {
                lastBdCopy = Database.getInstance().getDbCopy();
            }
        }

        int snaps = FileUtils.listFiles(new File(currentDirectory),
                new String[]{"snap"}, false).size();


        if (snaps >= snapFactor) {
            // delete oldest file (snap and log)
            int number = currentSnap - snapFactor;
            String snapName = String.format("snapshot.%d.snap", number);
            File fileToDelete = new File(snapName);

            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
        }

        FileOutputStream fw = null;
        ObjectOutputStream printWriter = null;
        String fileName = "snapshot."+ currentSnap +".snap";
        try {
            File f = new File(fileName);
            f.createNewFile();

            fw = new FileOutputStream(f,true);
            printWriter = new ObjectOutputStream(fw);
            printWriter.writeObject(db);
            printWriter.flush();

            currentSnap++;
            //logger.info(String.format("Snapshot %s created", fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                printWriter.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public final void stop() {
        this.run = false;
    }

    @Override
    public void run() {
        while (run)
        {
            try {
                Thread.sleep(timer);
                snap();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
