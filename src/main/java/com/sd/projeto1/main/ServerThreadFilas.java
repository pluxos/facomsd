package com.sd.projeto1.main;

import com.sd.projeto1.util.PropertyManagement;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class ServerThreadFilas implements Runnable {

    private static Map<BigInteger, String> mapa = new HashMap();
    private DatagramSocket socketServidor;
    private static PropertyManagement pm;
    private static byte[] in;
    private ExecutorService executor;

    /// Recebendo o pacote da Thread Anterior;
    ServerThreadFilas(DatagramSocket socketServidor) {
        this.socketServidor = socketServidor;
    }

    @Override
    public void run() {
        try {
            executor = Executors.newCachedThreadPool();
            pm = new PropertyManagement();

            while (true) {
                in = new byte[1400];
                DatagramPacket receivedPacket = MultiQueue.getComandoFila();
                
                if(receivedPacket != null){
                
                    MultiQueue.setDiscoFila(receivedPacket);
                    MultiQueue.setProcessamentoFila(receivedPacket);

                    ServerThreadDisk serverSend = new ServerThreadDisk(socketServidor);

                    if (serverSend != null) {
                        executor.execute(serverSend);
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
