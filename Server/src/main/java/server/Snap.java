package server;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TimerTask;

import singletons.Banco;

class Snap extends TimerTask {
    private Path path;

    @Override
    public synchronized void run() {
        Banco.getInstance().blockDatabase();
        path = Paths.get("./snap." + Banco.getInstance().getNumber());
        try {
            if (!Files.exists(path))
                Files.createFile(path);
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