package br.ufu;

import br.ufu.listener.F1Listener;
import br.ufu.listener.F2Listener;
import br.ufu.listener.F3Listener;
import br.ufu.listener.ServerListener;
import br.ufu.repository.CrudRepository;
import br.ufu.service.CrudService;
import br.ufu.service.QueueService;
import br.ufu.service.StartupRecoverService;
import br.ufu.writer.LogWriter;

import java.io.IOException;

import static br.ufu.util.Constants.PROPERTY_LOG_PATH;
import static br.ufu.util.Constants.PROPERTY_SERVER_PORT;
import static br.ufu.util.UserParameters.get;
import static br.ufu.util.UserParameters.getInt;

public class ServerApp {


    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = new Server();
        server.start();
    }

    private static void run() throws IOException, InterruptedException {


    }


}
