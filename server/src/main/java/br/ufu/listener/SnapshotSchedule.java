package br.ufu.listener;

import br.ufu.exception.SnapshotException;
import br.ufu.repository.CrudRepository;
import br.ufu.writer.SnapshotWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

public class SnapshotSchedule implements Runnable {

    private static final Logger log = LogManager.getLogger(F1Listener.class);
    private boolean running = true;
    private final CrudRepository crudRepository;
    private Integer snapshotNumber = 0;

    public SnapshotSchedule(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public void stop() {
        this.running = false;
    }

    private Integer getSnapshotNumber() {
        snapshotNumber = snapshotNumber + 1;
        return snapshotNumber;
    }

    private SnapshotWriter createSnapshot() throws IOException {
        Integer snapshotNumber = getSnapshotNumber();
        SnapshotWriter snapshot = new SnapshotWriter(snapshotNumber);
        return snapshot;
    }

    @Override
    public void run() {
        while(running) {
            try {
                Thread.sleep (50000);
                SnapshotWriter snapshotWriter = createSnapshot();
                Map<BigInteger, String> database = crudRepository.getDatabase();
                for (Map.Entry<BigInteger, String> item : database.entrySet()) {
                    snapshotWriter.write(item.getKey(), item.getValue());
                }
                log.info("Snapshot " + snapshotNumber + " gerado!");
            } catch (InterruptedException | IOException e) {
                log.warn(e.getMessage(), e);
                throw new SnapshotException(e);
            }
        }
    }
}
