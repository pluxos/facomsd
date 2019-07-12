/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd.projeto1.main;

import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.model.MapaDTO;
import com.sd.projeto1.util.PropertyManagement;
import org.apache.commons.lang3.SerializationUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerThreadDisk implements Runnable {

    public static Map<Long, String> mapa = new HashMap();
    private DatagramSocket socketServidor;
    private static PropertyManagement pm;
    private static byte[] in;
    private ExecutorService executor;

    /// Recebendo o pacote da Thread Anterior;
    ServerThreadDisk(DatagramSocket socketServidor) {
        this.socketServidor = socketServidor;
    }

    @Override
    public void run() {
        try {
            executor = Executors.newCachedThreadPool();
            pm = new PropertyManagement();
            // socketServidor = new DatagramSocket(pm.getPort());

            while (true) {
                in = new byte[1400];
                DatagramPacket receivedPacket = MultiQueue.getDiscoFila();
                if (receivedPacket != null) {

                    Mapa maparetorno = new Mapa();
                    maparetorno = (Mapa) SerializationUtils.deserialize(receivedPacket.getData());

                    MapaDTO mapaDisco = new MapaDTO();


                    ServerThreadSend serverSend = new ServerThreadSend(mapaDisco, socketServidor);

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
