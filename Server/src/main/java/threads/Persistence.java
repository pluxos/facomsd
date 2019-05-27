package threads;

import model.ItemFila;
import singletons.Banco;
import singletons.F3;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Persistence implements Runnable {
    private Timer timer;
    protected BlockingQueue<ItemFila> f3;
    private Banco Database = Banco.getInstance();
    

    public Persistence() {
        this.f3 = F3.getInstance();
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new RemindTask(), 0, 1000);
    }

    class RemindTask extends TimerTask {
        private Path path;
        @Override
        public synchronized void run() {
            Banco.getInstance().blockDatabase();

            path = Paths.get("./snap." + Banco.getInstance().getNumber());
            try {
                if (!Files.exists(path)) Files.createFile(path);
                Files.write(path, Banco.getInstance().toString().getBytes());
                if (Banco.getInstance().getNumber() > 3)
                    Files.deleteIfExists(Paths.get("./snap." + (Banco.getInstance().getNumber() - 3)));
            } catch (IOException e) {
                System.out.println("Erro no snapshot: " + e.getMessage());
            }

            Banco.getInstance().counterIncrement();
            Banco.getInstance().freeDatabase();
        }

    }

    @Override
    public void run() {
        String callback = null; // Mensagem de sucesso ou falha
        byte[] response;
        ItemFila obj;
        int type; // type == 1 string, type == 2 byte
        DataOutputStream output = null;

        try {
            while (true) {
                if (Banco.getInstance().isFree()) {
                    response = null;
                    obj = f3.take();
                    type = 1;

                    if (obj.getSocket() != null) {
                        output = new DataOutputStream(obj.getSocket().getOutputStream());
                    }

                    if (new String(obj.getControll()).equals("CREATE")) {
                        callback = (Database.Insert(new BigInteger(obj.getKey()), obj.getValue())) ? "CREATE SUCESS!"
                                : "CREATE FAIL!";
                    } else if (new String(obj.getControll()).equals("UPDATE")) {
                        callback = (Database.Update(new BigInteger(obj.getKey()), obj.getValue())) ? "UPDATE SUCESS!"
                                : "UPDATE FAIL!";
                    } else if (new String(obj.getControll()).equals("DELETE")) {
                        callback = (Database.Delete(new BigInteger(obj.getKey()))) ? "DELETE SUCESS!" : "DELETE FAIL!";
                    } else if (new String(obj.getControll()).equals("READ")) {
                        response = Database.Read(new BigInteger(obj.getKey()));
                        callback = "READ FAIL!";
                        if (response != null) {
                            type = 2;
                        }
                    } else {
                        System.out.println("ERROR");
                    }

                    if (obj.getSocket() == null) {
                        type = 3;
                    }
                    if (type == 1) {
                        byte[] messageBytesCommand = callback.getBytes();
                        output.writeInt((messageBytesCommand.length * 10 + type));
                        output.write(messageBytesCommand);
                    } else if (type == 2) {
                        output.writeInt((response.length * 10 + type));
                        output.write(response);
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}