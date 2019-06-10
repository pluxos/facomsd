package server;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TimerTask;

import singletons.Banco;

class Snap extends TimerTask {
    private Path path;
    private String snapString = new String();

    public Snap(){
        snapString += Table.getInstance().getMyKey();
        snapString += "/snap.";
    }

    @Override
    public synchronized void run() {
        Banco.getInstance().blockDatabase();
        path = Paths.get(snapString + Banco.getInstance().getNumber());
        try {
            if (!Files.exists(path))
                Files.createFile(path);
            Files.write(path, Banco.getInstance().toString().getBytes());
            if (Banco.getInstance().getNumber() > 3)
                Files.deleteIfExists(Paths.get(snapString + (Banco.getInstance().getNumber() - 3)));
        } catch (IOException e) {
            System.out.println("Erro no snapshot: " + e.getMessage());
        }

        Banco.getInstance().counterIncrement();
        Banco.getInstance().freeDatabase();
    }

}