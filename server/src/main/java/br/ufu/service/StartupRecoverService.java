package br.ufu.service;

import br.ufu.exception.InvalidCommandException;
import br.ufu.util.UserParameters;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static br.ufu.util.Constants.PROPERTY_LOG_PATH;
import static br.ufu.util.Constants.PROPERTY_SNAP_PATH;
import static java.nio.charset.Charset.defaultCharset;

public class StartupRecoverService {

    private static final Logger log = LogManager.getLogger(StartupRecoverService.class);
    private final CrudService crudService;
    private final UserParameters userParameters;
    private final BigInteger serverId;

    public StartupRecoverService(CrudService crudService, UserParameters userParameters, BigInteger serverId) {
        this.crudService = crudService;
        this.userParameters = userParameters;
        this.serverId = serverId;
    }

    private String getSnap() {
        File[] files = new File(userParameters.get(PROPERTY_SNAP_PATH)
                + "snaps-server-" + serverId.toString()).listFiles();
        Arrays.sort(files);
        return files[files.length - 1].getAbsolutePath();
    }

    private String getLog() {
//        File[] logs = new File(userParameters
//                .get(PROPERTY_LOG_PATH) + "logs-server-" + serverId).listFiles();
//        return logs[logs.length - 1].getAbsolutePath();
        return userParameters.get(PROPERTY_LOG_PATH);
    }

    public void recover() {
        try {

            String snapFile = getSnap();
            System.out.println(snapFile);
            List<String> snapshot = FileUtils.readLines(new File(snapFile), defaultCharset());
            snapshot.forEach(command -> {
                try {
                    crudService.execute("CREATE " + command);
                } catch (InvalidCommandException e) {
                    log.warn("Could not execute command on recover", e);
                }
            });

            String logFile = getLog();
            List<String> commands = FileUtils.readLines(new File(logFile), defaultCharset());
            commands.forEach(command -> {
                try {
                    System.out.println(command);
                    crudService.execute(command);
                } catch (InvalidCommandException e) {
                    log.warn("Could not execute command on recover", e);
                }
            });

        } catch (IOException e) {
            log.warn("Could not open log or snap file for recover", e);
        }
    }
}
