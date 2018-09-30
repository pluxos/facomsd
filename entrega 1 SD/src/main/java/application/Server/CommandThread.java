package application.Server;


import application.Process;
import application.Server.Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;


public class CommandThread extends Thread {

    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket clientSocket;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    //thread recebendo comando e colocando em
    //uma fila F1
    @Override
    public void run() {

        while (true) {

            Socket socket = this.clientSocket;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int ch;
            try {
                while (((ch = this.getInputStream().read()) != '\n')) {
                    if (ch == -1)
                        break;
                    baos.write(ch);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String data = baos.toString();

            Process process;
            if (!data.equals("")) {
                synchronized (Server.f1) {

                    process = new Process(data, this.getInputStream(), this.getOutputStream(), this.getClientSocket());
                    Server.f1.add(process);
                }
            } else break;

        }

    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}



