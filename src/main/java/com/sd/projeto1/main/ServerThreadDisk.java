/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd.projeto1.main;

import com.sd.projeto1.dao.MapaDao;
import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.model.MapaDTO;
import com.sd.projeto1.util.PropertyManagement;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerThreadDisk implements Runnable {


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
                    mapaDisco = tipoOperacao(maparetorno);

                    ServerThreadSend serverSend = new ServerThreadSend(mapaDisco, socketServidor);

                    if (serverSend != null) {
                        executor.execute(serverSend);
                    }
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
        }
    }





    public MapaDTO tipoOperacao(Mapa mapaEntity) throws Exception {

        MapaDTO mapaDTO = new MapaDTO();

        switch (mapaEntity.getTipoOperacaoId()) {
            case 1:


                if (mapaEntity != null) {
                    mapaDTO.setMapa(mapaEntity);
                    MapaDao.salvar(mapaEntity);
                    MapaDao.imprimeCRUD(mapaEntity);
                    mapaDTO.setMensagem("Inserido com Sucesso!");

                } else {
                    mapaDTO.setMensagem("Erro ao inserir!");
                }
                break;
            case 2:

                if (mapaEntity != null) {
                    mapaDTO.setMapa(mapaEntity);
                    MapaDao.editar(mapaEntity);
                    MapaDao.imprimeCRUD(mapaEntity);
                    mapaDTO.setMensagem("Atualizado com Sucesso!");

                } else {
                    mapaDTO.setMensagem("Erro ao atualizar!");
                }
                break;
            case 3:


                if (mapaEntity != null) {
                    mapaEntity.setTipoOperacaoId(3);
                    mapaDTO.setMapa(mapaEntity);
                    MapaDao.excluir(mapaEntity);
                    MapaDao.imprimeCRUD(mapaEntity);
                    mapaDTO.setMensagem("Excluido com Sucesso!");

                } else {
                    mapaDTO.setMensagem("Excluido ao atualizar!");
                }
                break;
            case 4:


                if (mapaEntity.getChave() >= 0) {
                    mapaEntity.setTipoOperacaoId(4);

                    String msg = MapaDao.buscar(mapaEntity);

                    System.out.println("Chave: " + mapaEntity.getChave());
                    System.out.println("Texto: " + msg);
                    MapaDao.imprimeCRUD(mapaEntity);
                    mapaEntity.setTexto(msg);
                    mapaDTO.setMapa(mapaEntity);
                    mapaDTO.setMensagem(msg);

                } else {
                    mapaDTO.setMensagem("Chave não válida!");
                }
                break;
            default:
                mapaDTO.setMapa(null);
                mapaDTO.setMensagem("Opção inválida");

        }

        return mapaDTO;
    }

}
