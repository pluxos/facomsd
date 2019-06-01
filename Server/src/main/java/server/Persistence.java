package server;

import server.ItemFila;;
import java.math.BigInteger;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;
import singletons.Banco;
import singletons.F3;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

import java.io.DataOutputStream;
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

    @Override
    public void run() {
        String callback = null; // Mensagem de sucesso ou falha
        ItemFila obj;
        CreateResponse responseC = null;
        UpdateResponse responseU = null;
        ReadResponse responseR = null;
        DeleteResponse responseD = null;

        try {
            while (true) {
                if (Banco.getInstance().isFree()) {
                    obj = f3.take();

                    if (obj.getControll().toUpperCase().equals("CREATE")) {
                        responseC = (Database.Insert(new BigInteger(obj.getKey()), obj.getValue()))
                                ? CreateResponse.newBuilder().setRetorno(true).build()
                                : CreateResponse.newBuilder().setRetorno(false).build();
                        obj.getResponseC().onNext(responseC);
                        obj.getResponseC().onCompleted();

                    } else if (obj.getControll().toUpperCase().equals("UPDATE")) {
                        responseU = (Database.Update(new BigInteger(obj.getKey()), obj.getValue()))
                                ? UpdateResponse.newBuilder().setRetorno(true).build()
                                : UpdateResponse.newBuilder().setRetorno(false).build();
                        obj.getResponseU().onNext(responseU);
                        obj.getResponseU().onCompleted();

                    } else if (obj.getControll().toUpperCase().equals("DELETE")) {
                        responseD = (Database.Delete(new BigInteger(obj.getKey())))
                                ? DeleteResponse.newBuilder().setRetorno(true).build()
                                : DeleteResponse.newBuilder().setRetorno(false).build();
                        obj.getResponseD().onNext(responseD);
                        obj.getResponseD().onCompleted();

                    } else if (obj.getControll().toUpperCase().equals("READ")) {
                        byte[] resposta = Database.Read(new BigInteger(obj.getKey()));
                        ByteString retorno;
                        int size;
                        if (resposta != null) {
                            retorno = ByteString.copyFrom(resposta);
                            size = resposta.length;
                            responseR = ReadResponse.newBuilder().setValue(retorno).setValuesize(size).build();
                        } else {
                            retorno = ByteString.copyFrom((new String("READ FAILL!!!").getBytes()));
                            size = (new String("READ FAILL!!!").getBytes()).length;
                            responseR = ReadResponse.newBuilder().setValue(retorno).setValuesize(size).build();
                        }
                        obj.getResponseR().onNext(responseR);
                        obj.getResponseR().onCompleted();
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}