package com.sd.projeto1.main;

import com.sd.projeto1.dao.MapaDao;
import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.proto.SubscribeResponse;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.StringUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ThreadAlertSubscribes implements Runnable {

	private Queue< String > logQueue;
	private Queue< String > executeQueue;
	private DatagramSocket serverSocket;
	private Map< String, List<StreamObserver< SubscribeResponse >> > observers;
	
	public ThreadAlertSubscribes(
		DatagramSocket serverSocket,
		Queue< String > logQueue,
		Queue< String > executeQueue,
		Map< String, List<StreamObserver< SubscribeResponse >> > observers ) {
		this.logQueue = logQueue;
		this.executeQueue = executeQueue;
		this.serverSocket = serverSocket;
		this.observers = observers;
	}

	@Override
	public void run() {

		while (true) {
                    try {
                        //Thread.sleep( 20000 );
                        String instruction = executeQueue.poll();
                        if ( instruction != null ) {
                                List<String> params = Arrays.asList(instruction.split( ";" ));
                            
                                System.out.println( "Foi executada a instrucao de: " + params.get(0));
                                execute( instruction );
                        }
                        Thread.sleep( 1 );
                    } catch ( Exception ex ) {
                            ex.printStackTrace();
                    }
		}
	}

	private void execute( String instruction ) {
		List<String> params = Arrays.asList(instruction.split( ";" ));
                
                Mapa mapa = new Mapa();
                mapa.setChave(Integer.parseInt(params.get(1)));
                
                
            switch (params.get(0).toUpperCase()) {
                case "PROCURAR":
                    if(!StringUtils.isBlank(MapaDao.buscar(mapa))){
                        alertSubscribers( params.get(1), "PROCURAR");
                    }
                    else{
                        sendDatagram(params);
                    }
                    
                    return;
                case "INSERIR":
                    mapa.setTexto(params.get(2));
					MapaDao.salvar(mapa);
                    alertSubscribers( params.get(1), "INSERIR");
                    break;
                case "ATUALIZAR":
					if(!StringUtils.isBlank(MapaDao.buscar(mapa))){
                        
                        mapa.setTexto(params.get( 2 ));
						MapaDao.editar(mapa);
                        alertSubscribers( params.get( 1 ), "ATUALIZAR" );
                    }
                    break;
                default:
					MapaDao.excluir(mapa);
                    alertSubscribers( params.get( 1 ), "EXCLUIR" );
                    break;
            }

		logQueue.add( instruction );

	}

	
	private void sendDatagram( List<String> params ) {
		try {
			System.out.println( "Enviando o contexto para o remetente" );
			byte[] sendData;
			if( params.size() < 4 ) {
				sendData = MapaDao.buscarTodos().getBytes();
				DatagramPacket sendPacket = new DatagramPacket( sendData, sendData.length,
					InetAddress.getByName( params.get( 1 ).replace( "/", "" ) ), Integer.parseInt( params.get( 2 ) ) );
				serverSocket.send( sendPacket );
			} else {
                            Mapa mapa = new Mapa();
                            mapa.setChave(Integer.parseInt(params.get(1)));
                            
				sendData = MapaDao.buscar(mapa).getBytes();
				DatagramPacket sendPacket = new DatagramPacket( sendData, sendData.length,
					InetAddress.getByName( params.get( 2 ).replace( "/", "" ) ), Integer.parseInt( params.get( 3 ) ) );
				serverSocket.send( sendPacket );
			}
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
	}
	
	private void alertSubscribers( String key, String changed ) {
	
		try {
			if( observers.get( key ) != null ) {
				List<StreamObserver<SubscribeResponse>> observerList = observers.get(key);
				for(StreamObserver<SubscribeResponse> observer : observerList) {
					SubscribeResponse response = SubscribeResponse.newBuilder().setMessage("=====================================\n" +
                                                    "   --MONITORAMENTO DE CHAVE--\nA chave: '" + key + "' foi acionada pela instrucao de " + changed +
                                                    "\n=====================================\n").build();
					observer.onNext(response);
				}
			}
		} catch( Exception ex ) {
		}
		
	}

}
