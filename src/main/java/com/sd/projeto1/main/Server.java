package com.sd.projeto1.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramSocket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;

import com.sd.projeto1.dao.MapaDao;
import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.util.PropertyManagement;
import com.sd.projeto1.proto.SubscribeResponse;
import io.grpc.stub.StreamObserver;

public class Server {


	private static PropertyManagement mySettings = new PropertyManagement();
	private static DatagramSocket serverSocket;
	private static Queue< String > logQueue = new LinkedList< String >();
	private static Queue< String > executeQueue = new LinkedList< String >();
	private static Map< String, List< StreamObserver< SubscribeResponse > > > observers = new HashMap< String, List< StreamObserver< SubscribeResponse > > >();
	private static ExecutorService executor;

	public static void main(String[] args) throws Exception {
		List<Mapa> logs = new ArrayList<Mapa>();

		loadData();
		MapaDao.imprimeMapa();

		System.out.println("Log do Disco Recuperado");
		System.out.println("Tamanho da Fila: " + MapaDao.mapa.size() + "\n");

		System.out.println("Servidor Iniciado...");
        new Thread(new ServerThreadReceive()).start();

		ThreadAlertSubscribes executorThread = new ThreadAlertSubscribes( serverSocket, logQueue, executeQueue,  observers );

		ServerThreadGRPC grpcServerThread = new ServerThreadGRPC( logQueue, executeQueue,  mySettings, observers );

		executor.execute( grpcServerThread );
		executor.execute( executorThread );
	}

	private static void loadData() {
		Mapa mapa = new Mapa();
		StringBuilder sb = new StringBuilder();

		try (BufferedReader br = Files.newBufferedReader(Paths.get("app.log"))) {

			String line;
			while ((line = br.readLine()) != null) {
				String[] content = line.split("#");
				if(content.length>0){
					mapa.setTipoOperacaoId(Integer.parseInt(content[0]));
					mapa.setChave(Integer.parseInt(content[1]));
					if(content.length>2)
						mapa.setTexto(content[2]);

					if(mapa.getTipoOperacaoId() == 1 || mapa.getTipoOperacaoId() == 2)
						MapaDao.mapa.put(new BigInteger(String.valueOf(mapa.getChave())), mapa.getTexto());
					else if(mapa.getTipoOperacaoId() == 3)
                        MapaDao.mapa.remove(new BigInteger(String.valueOf(mapa.getChave())));
				}

				sb.append(line).append("\n");
			}

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}

		System.out.println(sb);

	}
}
