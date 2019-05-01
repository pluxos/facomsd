package br.ufu.ds.server;

import br.ufu.ds.ServerProtocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;

public class DatabaseProducer implements Runnable {

    private final Database mDatabase = Database.getInstance();

    public final void produce() throws IOException, DatabaseException {
        File file = new File("log.bin");
        if (!file.exists()) {
            file.createNewFile();
            return;
        }

        FileInputStream log = new FileInputStream(file);

        ServerProtocol.Request request = null;
        while ((request = ServerProtocol.Request.parseDelimitedFrom(log)) != null) {
            try {
                switch (request.getRequestType().getNumber()) {
                    case ServerProtocol.Request.RequestType.CREATE_VALUE:
                        mDatabase.create(BigInteger.valueOf(request.getId()), request.getData());
                        break;

                    case ServerProtocol.Request.RequestType.DELETE_VALUE:
                        mDatabase.delete(BigInteger.valueOf(request.getId()));
                        break;

                    case ServerProtocol.Request.RequestType.UPDATE_VALUE:
                        mDatabase.update(BigInteger.valueOf(request.getId()), request.getData());
                        break;
                }
            } catch (Exception e) {
                // ignore
            }
        }


        try {
            log.close();
        } catch (Exception e) {
            // ignore
        }
    }


    @Override
    public final void run() {
        try {
            produce();
        } catch (IOException | DatabaseException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
