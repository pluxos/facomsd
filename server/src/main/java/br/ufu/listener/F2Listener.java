package br.ufu.listener;

import br.ufu.exception.ListenerException;
import br.ufu.model.Command;
import br.ufu.service.QueueService;
import br.ufu.writer.LogWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Comparator;

public class F2Listener extends FxListener {

    private static final Logger log = LogManager.getLogger(F2Listener.class);
    private static final String FILE_SEPARATOR = "\n";
    private BigInteger logNumber = new BigInteger("0");
    private final BigInteger serverId;
    private final String logPath;

    private final QueueService queueService;

    private LogWriter logWriter;

    public F2Listener(QueueService queueService, String logPath, BigInteger serverId) {
        this.queueService = queueService;
        this.logPath = logPath;
        this.serverId = serverId;
    }

    public void startLogNumber(String logN) {
        if (StringUtils.isBlank(logN)) {
            logN = "0";
            this.logNumber = new BigInteger(logN);
            try {
                this.logWriter = new LogWriter(getLogPath(), new BigInteger(logN));
            } catch (IOException e) {
                throw new ListenerException(e);
            }
        } else {
            Integer logI = Integer.valueOf(logN) + 1;
            logN = String.valueOf(logI);
            this.logNumber = new BigInteger(logN);
            try {
                this.logWriter = new LogWriter(getLogPath(), new BigInteger(logN));
            } catch (IOException e) {
                throw new ListenerException(e);
            }

        }
    }

    private BigInteger getLogNumber() {
        logNumber = logNumber.add(new BigInteger("1"));
        return logNumber;
    }

    public void setNewLog() throws IOException {
        this.logWriter.getWriter().close();
        BigInteger logNumber = getLogNumber();
        LogWriter log = new LogWriter(getLogPath(), logNumber);
        this.logWriter = log;
        controlLogNumber(getLogPath());
    }

    public String getLogPath() {
        return logPath + "logs-server-" + serverId.toString();
    }

    private static void controlLogNumber(String logPath){

        File logDirectory = new File(logPath);
        if(logDirectory.isDirectory()) {
            File[] listLogs = logDirectory.listFiles();
            Arrays.sort(listLogs, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.compare(f1.lastModified(), f2.lastModified());
                }
            });
            if (listLogs.length > 3) {
                if (listLogs[0].delete())
                    System.out.println("  Deleted!");
                else
                    System.out.println("  Delete failed - reason unknown");
            }
        }
    }

    @Override
    protected void listen() {
        try {
            Command item = queueService.consumeF2();
            log.info("F2 Listener take command [{}]", item.getExecuteCommand());
            if (commadIsRead(item)) {
                return;
            }
            logWriter.write(item.getExecuteCommand() + FILE_SEPARATOR);

        } catch (InterruptedException | IOException e) {
            throw new ListenerException(e);
        }
    }

    private boolean commadIsRead(Command item) {
        return "READ".equals(item.getExecuteCommand().split(" ")[0]);
    }
}
