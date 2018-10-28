package br.ufu.listener;

import br.ufu.exception.SnapshotException;
import br.ufu.repository.CrudRepository;
import br.ufu.writer.SnapshotWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

public class SnapshotSchedule implements Runnable {

    private static final Logger log = LogManager.getLogger(SnapshotSchedule.class);
    private boolean running = true;
    private final CrudRepository crudRepository;
    private BigInteger snapshotNumber = new BigInteger("0");
    private Integer snapTime;
    private BigInteger serverId;
    private String snapPath;
    private F2Listener f2;

    public SnapshotSchedule(CrudRepository crudRepository, F2Listener f2, Integer snapTime, String snapPath, BigInteger serverId) {
        this.crudRepository = crudRepository;
        this.snapTime = snapTime;
        this.snapPath = snapPath;
        this.serverId = serverId;
        this.f2 = f2;
    }

    public void stop() {
        this.running = false;
    }

    private BigInteger getSnapshotNumber() {
        snapshotNumber = snapshotNumber.add(new BigInteger("1"));
        return snapshotNumber;
    }

    public String getSnapshotPath() {
        return snapPath + "snaps-server-" + serverId.toString();
    }

    private SnapshotWriter createSnapshot() throws IOException {
        BigInteger snapshotNumber = getSnapshotNumber();
        SnapshotWriter snapshot = new SnapshotWriter(getSnapshotPath(), snapshotNumber);
        return snapshot;
    }

    private static void controlSnapNumber(String snapPath){
        File snapDirectory = new File(snapPath);
        if(snapDirectory.isDirectory()) {
            File[] listSnaps = snapDirectory.listFiles();
            if (listSnaps.length > 3) {
                if (listSnaps[0].delete())
                    System.out.println("  Deleted!");
                else
                    System.out.println("  Delete failed - reason unknown");
            }
        }
    }

    @Override
    public void run() {
        while(running) {
            try {
                Thread.sleep (snapTime);
                SnapshotWriter snapshotWriter = createSnapshot();
                f2.setNewLog();
                Map<BigInteger, String> database = crudRepository.getDatabase();
                for (Map.Entry<BigInteger, String> item : database.entrySet()) {
                    snapshotWriter.write(item.getKey(), item.getValue());
                }
                snapshotWriter.getWriter().close();
                log.info("Snapshot " + snapshotNumber + " gerado!");
                controlSnapNumber(getSnapshotPath());
            } catch (InterruptedException | IOException e) {
                log.warn(e.getMessage(), e);
                throw new SnapshotException(e);
            }
        }
    }
}
