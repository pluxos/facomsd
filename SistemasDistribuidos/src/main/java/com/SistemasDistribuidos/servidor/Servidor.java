package com.SistemasDistribuidos.servidor;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

/**
 * 
 * @author luizw
 *
 */
public class Servidor  extends StateMachine {
	
	static Logger log = Logger.getLogger(Servidor.class.getName());
	private Map<Long,String> maps = new HashMap<>();
	
	/**
	 * 
	 * @param commit
	 * @return
	 */
	public Boolean PostCommand(Commit<com.SistemasDistribuidos.utils.PostCommand> commit){
		try{
			com.SistemasDistribuidos.utils.PostCommand postcommand = commit.operation();
			maps.put(postcommand.id,postcommand.message);

			System.out.println("Adicionando " + maps);

			return true;
		}finally{
			commit.close();
		}
	}
	
	/**
	 * 
	 * @param commit
	 * @return
	 */
	public String GetCommand(Commit<com.SistemasDistribuidos.utils.GetCommand> commit){
		try{
			com.SistemasDistribuidos.utils.GetCommand getcommand = commit.operation();
			String message = maps.get(getcommand.id);

			System.out.println("Key "+getcommand.id+" : " + message);

			return message;
		}finally{
			commit.close();
		}
	}
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {		
		
		args = new String[7];
		args[0] = "0";
		args[1]	= "127.0.0.1";     
		args[2] = "8080";
		args[3] = "127.0.0.1";
		args[4] = "8081";
		args[5] = "127.0.0.1";
		args[6] = "8082";

        int ID = Integer.parseInt(args[0]);
        List<Address> addresses = new LinkedList<>();

        for(int i = 1; i <args.length; i+=2){
            Address address = new Address(args[i], Integer.parseInt(args[i+1]));
            addresses.add(address);
        }

        CopycatServer.Builder builder = CopycatServer.builder(addresses.get(ID))
                .withStateMachine(Servidor::new).withTransport( NettyTransport.builder().withThreads(5).build())
                .withStorage( Storage.builder().withDirectory(new File("logs_"+ID)).withStorageLevel(StorageLevel.DISK).build());
        CopycatServer servidor = builder.build();
        
        try {
            if(ID == 0){
            	System.out.print("\nIniciando aplicação distribuida\n\n");
                servidor.bootstrap();
            }
            else{
                servidor.join(addresses);
            }
            
    		System.out.print("Tamanho da fila: " + ServidorProcessos.mapa.size() + "\n\n");

    		System.out.print("Iniciando Servidor ...\n\n");
    		System.out.print("... ... ...\n");
        }catch(Exception e) {
        	log.info("ERRO: "+e.getMessage());
        }
	
	}
}
	