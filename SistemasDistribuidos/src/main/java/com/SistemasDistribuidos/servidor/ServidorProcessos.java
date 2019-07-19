/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SistemasDistribuidos.servidor;

import com.SistemasDistribuidos.models.MapData;
import com.SistemasDistribuidos.models.MapaProcessos;
import com.SistemasDistribuidos.utils.Filas;
import com.SistemasDistribuidos.utils.FileUtils;
import com.SistemasDistribuidos.utils.ManagementProperties;

import io.atomix.catalyst.transport.Client;
import io.atomix.copycat.server.StateMachine;

import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author luizw
 *
 */
public class ServidorProcessos extends StateMachine implements Runnable {
	
	Logger log = Logger.getLogger(ServidorProcessos.class.getName());

    public static Map<Integer, String> mapa = new HashMap();
    private DatagramSocket socketServidor;
    private static ManagementProperties management;
    private static byte[] in;
    private ExecutorService executorService;

    /**
     * RECEIVE PACKAGE
     * @param socketServidor
     */
    ServidorProcessos(DatagramSocket socketServidor) {
        this.socketServidor = socketServidor;
    }

    @Override
    public void run() {
        try {
        	
        	executorService = Executors.newCachedThreadPool();
            management = new ManagementProperties();
            

            while (true) {
                in = new byte[1400];
                DatagramPacket receivedPacket = Filas.getFila();
                if (receivedPacket != null) {

                    MapData maparetorno = new MapData();
                    maparetorno = (MapData) SerializationUtils.deserialize(receivedPacket.getData());

                    MapaProcessos mapaDisco = new MapaProcessos();
                    mapaDisco = tipoOperacao(maparetorno);

                    ServidorEnvio serverSend = new ServidorEnvio(mapaDisco, socketServidor);

                    if (serverSend != null) {
                    	executorService.execute(serverSend);
                    }
                }

            }
        } catch (IOException ex) {
            log.info("Exception IO: "+ ex.getMessage());
        } catch (Exception e) {
        	log.info("Exception: "+ e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
	public static void salvar(MapData mapValue) {
        Integer key = new Integer(String.valueOf(generateKey()));
        HashMap<Integer, String> a = mapValue.getMapa();
        BigInteger chave = ((Entry<BigInteger, String>) a).getKey();
        String msg = a.get(chave);
        

        if (mapa.containsKey(chave)) {
            System.out.println("Essa key já existe");
        }
        FileUtils.writeFileLog(String.valueOf(FileUtils.CREATE), key, msg);
        mapa.put(key,msg);
    }

    private static int generateKey() {
        return mapa.size();
    }


    public static void editar(MapData mapData) {
    	HashMap<Integer, String> a = mapData.getMapa();
        Integer chave = ((Entry<Integer, String>) a).getKey();        
        String texto = a.get(chave);

        if (!mapa.containsKey(chave)) {
            System.out.println("Chave não encontrada");
        }
        FileUtils.writeFileLog(String.valueOf(FileUtils.UPDATE), chave, texto);
        mapa.put(chave, texto);
    }
    
    
    public static void excluir(MapData mapData) {
    	HashMap<Integer, String> a = mapData.getMapa();
        Integer chave = ((Entry<Integer, String>) a).getKey();
        
        FileUtils.writeFileLog(String.valueOf(FileUtils.DELETE), chave, "");
        mapa.remove(chave);
    }

    public static String buscar(MapData mapData) {
    	HashMap<Integer, String> a = mapData.getMapa();
        Integer chave = ((Entry<Integer, String>) a).getKey();
        
        return mapa.get(chave);
    }

    public static void imprimeCRUD(MapData mapData) {
    	HashMap<Integer, String> a = mapData.getMapa();
    	Integer chave = ((Entry<Integer, String>) a).getKey();        
        String msg = a.get(chave);

        System.out.println("\n===============================");
        System.out.println("Chave: " + chave);
        System.out.println("Texto: " + msg);
//        System.out.println("Tipo de Operaçao: " + FileUtils.retornaTipoOperacao(mapData.getTipoOperacaoId()));
        System.out.println("Data: " + mapData.getData());
        System.out.println("Tamanho da fila: " + mapa.size());
        System.out.println("===============================");
    }

    public static void imprimeMapa() {
        for (Map.Entry<Integer, String> map : mapa.entrySet()) {

            System.out.println("\n=============================");
            System.out.println("Chave: " + map.getKey());
            System.out.println("Texto: " + map.getValue());
            System.out.println("===============================");
        }
    }

    @SuppressWarnings("unused")
	public MapaProcessos tipoOperacao(MapData mapData) throws Exception {
    	
    	HashMap<Integer, String> a = mapData.getMapa();
    	Integer chave = ((Entry<Integer, String>) a).getKey();        
        String msg = a.get(chave);

        MapaProcessos mapaProcessos = new MapaProcessos();

        switch (mapData.getOperation()) {
            case 1:
                if (mapData != null) {
                    mapaProcessos.setMapData(mapData);
                    salvar(mapData);
                    imprimeCRUD(mapData);
                    mapaProcessos.setMsg("Inserido com Sucesso!");

                } else {
                    mapaProcessos.setMsg("Erro ao inserir!");
                }
                break;
            case 2:
                if (mapData != null) {
                    mapaProcessos.setMapData(mapData);
                    editar(mapData);
                    imprimeCRUD(mapData);
                    mapaProcessos.setMsg("Atualizado com Sucesso!");

                } else {
                    mapaProcessos.setMsg("Erro ao atualizar!");
                }
                break;
            case 3:
                if (mapData != null) {
                	mapData.setOperation(3);
                    mapaProcessos.setMapData(mapData);
                    excluir(mapData);
                    imprimeCRUD(mapData);
                    mapaProcessos.setMsg("Excluido com Sucesso!");

                } else {
                    mapaProcessos.setMsg("Excluido ao atualizar!");
                }
                break;
            case 4:
                if (chave >= 0) {
                	mapData.setOperation(4);

                    String msg2 = buscar(mapData);

                    System.out.println("Chave: " + chave);
                    System.out.println("Texto: " + msg);
                    imprimeCRUD(mapData);
                    mapData.getMapa().replace(chave, msg);
                    mapaProcessos.setMapData(mapData);
                    mapaProcessos.setMsg(msg);

                } else {
                    mapaProcessos.setMsg("Chave inválida!");
                }
                break;
            default:
                mapaProcessos.setMapData(null);
                mapaProcessos.setMsg("Opção inválida");

        }

        return mapaProcessos;
    }

}
