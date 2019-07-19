package com.SistemasDistribuidos.servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.SerializationUtils;

import com.SistemasDistribuidos.models.MapaProcessos;
import com.SistemasDistribuidos.utils.Filas;
/**
 *
 * 
 */
public class ServidorEnvio implements Runnable {

    private DatagramSocket serverSocket;
    private MapaProcessos mapaProcessos;
    static Logger log = Logger.getLogger(ServidorEnvio.class.getName());
    
    public ServidorEnvio(MapaProcessos mapaProcessos, DatagramSocket serverSocket) {
        this.mapaProcessos = new MapaProcessos();
        this.mapaProcessos = mapaProcessos;
        this.serverSocket = serverSocket;
    }
    
    /**
     * 
     */
    @Override
    public void run() {
        try {
            DatagramPacket receivedPacket = Filas.getProcessaFila();
            if(receivedPacket != null){
                
                send(receivedPacket, mapaProcessos);
            }
           
        } catch (Exception ex) {
            log.log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param datagram
     * @param mapaDTO
     * @throws IOException
     */
    public void send(DatagramPacket datagram, MapaProcessos mapaProcessos) throws IOException {

        InetAddress IPAddress = datagram.getAddress();
        int port = 1234;
        byte mensagem[] = new byte[1400];

        mensagem = SerializationUtils.serialize(mapaProcessos);

        DatagramPacket sendPacket = new DatagramPacket(mensagem, mensagem.length, IPAddress, port);

        serverSocket.send(sendPacket);
    }

}
