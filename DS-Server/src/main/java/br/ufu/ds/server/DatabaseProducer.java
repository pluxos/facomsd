package br.ufu.ds.server;

import br.ufu.ds.rpc.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;

public final class DatabaseProducer implements Runnable {

    private final Database mDatabase = Database.getInstance();
    private final File lastLog;

    public DatabaseProducer(File lastLog) {
        this.lastLog = lastLog;
    }

    public final void produce() throws IOException {
        FileInputStream log = new FileInputStream(this.lastLog);

        Request request = null;
        while ((request = Request.parseDelimitedFrom(log)) != null) {
            try {
                switch (request.getRequestType().getNumber()) {
                    case Request.RequestType.CREATE_VALUE:
                        mDatabase.create(BigInteger.valueOf(request.getId()), request.getData());
                        break;

                    case Request.RequestType.DELETE_VALUE:
                        mDatabase.delete(BigInteger.valueOf(request.getId()));
                        break;

                    case Request.RequestType.UPDATE_VALUE:
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}