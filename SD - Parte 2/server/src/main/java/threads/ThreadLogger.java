package threads;

import model.Operator;

import java.util.List;

public class ThreadLogger extends Thread {

    private static ThreadLogger threadLogger;

    private ThreadLogger(){
    }

    static ThreadLogger init() {
        if(threadLogger == null) {
            threadLogger = new ThreadLogger();
            threadLogger.start();
        }
        return threadLogger;
    }

    @Override
    public void run(){
        while(true) {
            if(!DataStorage.getInstance().getLog().isEmpty()) {

                Operator op = DataStorage.getInstance().pollLog();
            }
        }
    }
}
