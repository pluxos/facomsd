package br.ufu.service;

import br.ufu.exception.InvalidCommandException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static br.ufu.util.Constants.PROPERTY_LOG_PATH;
import static br.ufu.util.UserParameters.get;
import static java.nio.charset.Charset.defaultCharset;

public class StartupRecoverService {

    private static final Logger log = LogManager.getLogger(StartupRecoverService.class);
    private final CrudService crudService;

    public StartupRecoverService(CrudService crudService) {
        this.crudService = crudService;
    }

    public void recover() {
        try {
            List<String> commands = FileUtils.readLines(new File(get(PROPERTY_LOG_PATH)), defaultCharset());
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
