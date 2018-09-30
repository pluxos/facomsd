package application.Server;

import application.Process;
import application.Server.Server;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ProcessThread extends Thread {

    final String CREATE = "1",
            READ = "2",
            UPDATE = "3",
            DELETE = "4";

    private String logFile;
    private Map<BigInteger, String> mapa = new HashMap<>();

    public ProcessThread(String logFile) {
        this.logFile = logFile;
    }

    @Override
    public void run() {
        processLogFile();
        boolean ok;
        String resposta;
        while (true) {
            Process process;
            synchronized (Server.f3) {
                process = Server.f3.poll();
            }
            if (process != null) {
                String[] splited = process.getRequest().split("'");
                String CRUD = splited[0];
                BigInteger key = new BigInteger(splited[1]);
                String value = splited[2];
                if (!CRUD.equals(READ)) {
                    ok = processCUD(CRUD, key, value);
                    if (ok == true) {
                        resposta = "OK";
                    } else {
                        resposta = "NOK";
                    }
                    try {
                        processReply(resposta, process);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        processReply(mapRead(key), process);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void processReply(String data, Process process) throws Exception {
        DataOutputStream outToClient = new DataOutputStream(
               process.getOutputStream());
        outToClient.writeBytes(data + '\n');

    }

    private boolean processCUD(String crud, BigInteger key, String value) {

        switch (crud) {
            case CREATE:
                return mapCreate(key, value);
            case UPDATE:
                return mapUpdate(key, value);
            case DELETE:
                return mapDelete(key);
            default:
                return false;
        }
    }

    private boolean mapDelete(BigInteger key) {

        mapa.remove(key);
        return true;
    }

    private boolean mapUpdate(BigInteger key, String value) {
        if (!mapa.containsKey(key))
            return false;
        mapa.replace(key, value);
        return true;
    }

    private String mapRead(BigInteger key) {
        if (!mapa.containsKey(key))
            return "NOK";
        return mapa.get(key);
    }

    private boolean mapCreate(BigInteger key, String value) {
        if (mapa.containsKey(key))
            return false;
        mapa.put(key, value);
        return true;
    }

    private void processLogFile() {
        String command;
        try {
            FileReader fileReader = new FileReader(logFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((command = bufferedReader.readLine()) != null) {
                bufferedReader.readLine();
                String[] splited = command.split("'");
                String CRUD = splited[0];
                BigInteger key = new BigInteger(splited[1]);
                String value = splited[2];
                if (!CRUD.equals(READ))
                    processCUD(CRUD, key, value);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            logFile + "'");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
