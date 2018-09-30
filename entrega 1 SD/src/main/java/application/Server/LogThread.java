package application.Server;

import application.Process;

import java.io.FileWriter;

public class LogThread extends Thread {

    static FileWriter fileWriter;

    private String logFile;

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }
//thread consumindo comandos de F2 e gravando-os em
    //disco

    public void run() {

        while (true) {

            try {
                Process process;

                synchronized (Server.f2) {
                    process = Server.f2.poll();
                }
                if (process != null) {
                    String data = process.getRequest();
                    if (data.charAt(0) != '2') {
                        fileWriter = new FileWriter(this.getLogFile(), true);
                        fileWriter.write(process.getRequest() + System.getProperty("line.separator"));
                        fileWriter.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
