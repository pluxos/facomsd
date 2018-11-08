package server;

import java.util.List;

public class ThreadLogger extends Thread {

    private static ThreadLogger threadLogger;

    static ThreadLogger init() {
        if( threadLogger == null ) {
            threadLogger = new ThreadLogger();
            threadLogger.start();
        }
        return threadLogger;
    }

    @Override
    public void run(){
        while( true ) {
            if(!DataStorage.getInstance().getLog().isEmpty()) {

                Operation operation = DataStorage.getInstance().pollLog();
                updateLogFile( operation );
            }
        }
    }

    private void updateLogFile( Operation operation) {
        FileStorageHelper.getInstance().saveLogData( operation );
    }

    public List< Operation > getLogList() {
        return FileStorageHelper.getInstance().recoverLogData();
    }
}
