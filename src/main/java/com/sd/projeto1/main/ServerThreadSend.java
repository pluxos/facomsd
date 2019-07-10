/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd.projeto1.main;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.SerializationUtils;

import com.sd.projeto1.model.MapaDTO;

/**
 *
 * 
 */
public class ServerThreadSend implements Runnable {

    private DatagramSocket serverSocket;
    private MapaDTO mapaDTO;
    private static byte[] in;
    private static Map<BigInteger, String> mapa = new HashMap();

    public ServerThreadSend(MapaDTO mapaDTO, DatagramSocket serverSocket) {
        this.mapaDTO = new MapaDTO();
        this.mapaDTO = mapaDTO;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            in = new byte[1400];

            DatagramPacket receivedPacket = MultiQueue.getProcessamentoFila();
            if(receivedPacket != null){
                
                send(receivedPacket, mapaDTO);
            }
           
        } catch (Exception ex) {
            Logger.getLogger(ServerThreadFilas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void send(DatagramPacket receivePacket, MapaDTO mapaDTO) throws IOException {

        InetAddress IPAddress = receivePacket.getAddress();
        int port = 7391;//receivePacket.getPort();
        byte mensagem[] = new byte[1400];

        mensagem = SerializationUtils.serialize(mapaDTO);

        DatagramPacket sendPacket = new DatagramPacket(mensagem, mensagem.length, IPAddress, port);

        serverSocket.send(sendPacket);
    }

}
