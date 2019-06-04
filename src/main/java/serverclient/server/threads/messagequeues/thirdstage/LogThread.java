package serverclient.server.threads.messagequeues.thirdstage;

import serverclient.server.database.LogFile;
import serverclient.server.threads.ServerThread;

public class LogThread implements Runnable {

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                LogFile.saveOperationLog(ServerThread.getFila2().take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
