package application.Cliente;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ResponserThread extends Thread {
    private Socket socket;

    @Override
    public void run() {
        try {
            InputStream inputStream = this.getSocket().getInputStream();
            OutputStream outputStream = this.getSocket().getOutputStream();
            while (true) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int ch;
                while (((ch = inputStream.read()) != '\n')) {
                    if (ch == -1)
                        break;
                    baos.write(ch);
                }
                String data = baos.toString();
                System.out.println(data + "\n");
                try {
                    Thread.sleep(5000l);
                } catch (InterruptedException e) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
