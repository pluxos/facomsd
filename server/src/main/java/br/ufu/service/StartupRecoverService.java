package br.ufu.service;

import br.ufu.exception.InvalidCommandException;
import br.ufu.util.UserParameters;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static br.ufu.util.Constants.PROPERTY_LOG_PATH;
import static java.nio.charset.Charset.defaultCharset;

public class StartupRecoverService {

    private static final Logger log = LogManager.getLogger(StartupRecoverService.class);
    private final CrudService crudService;
    private final UserParameters userParameters;

    public StartupRecoverService(CrudService crudService, UserParameters userParameters) {
        this.crudService = crudService;
        this.userParameters = userParameters;
    }

    public void recover() {
        try {
            String logFile = userParameters.get(PROPERTY_LOG_PATH);
            List<String> commands = FileUtils.readLines(new File(logFile), defaultCharset());
            commands.forEach(command -> {
                try {
                    crudService.execute(command);
                } catch (InvalidCommandException e) {
                    log.warn("Could not execute command on recover", e);
                }
            });
        } catch (IOException e) {
            log.warn("Could not open log file for recover", e);
        }
    }
}
