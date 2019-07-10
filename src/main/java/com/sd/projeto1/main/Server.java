package com.sd.projeto1.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.sd.projeto1.command.AddCommand;
import com.sd.projeto1.command.GetCommand;
import com.sd.projeto1.model.Mapa;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

public class Server extends StateMachine {


	private Map<Long,String> maps = new HashMap<>();

	public Boolean AddCommand(Commit<AddCommand> commit){
		try{
			AddCommand aec = commit.operation();
			maps.put(aec.id,aec.message);

			System.out.println("Adding " + maps);

			return true;
		}finally{
			commit.close();
		}
	}

	public String GetCommand(Commit<GetCommand> commit){
		try{
			GetCommand aec = commit.operation();
			String message = maps.get(aec.id);

			System.out.println("message key "+aec.id+" : " + message);

			return message;
		}finally{
			commit.close();
		}
	}

	public static void main(String[] args) throws Exception {
		List<Mapa> logs = new ArrayList<Mapa>();

		//loadData();
		//ServerThreadDisk.imprimeMapa();

        int myId = Integer.parseInt(args[0]);
        List<Address> addresses = new LinkedList<>();

        for(int i = 1; i <args.length; i+=2)
        {
            Address address = new Address(args[i], Integer.parseInt(args[i+1]));
            addresses.add(address);
        }

        CopycatServer.Builder builder = CopycatServer.builder(addresses.get(myId))
                .withStateMachine(Server::new)
                .withTransport( NettyTransport.builder()
                        .withThreads(4)
                        .build())
                .withStorage( Storage.builder()
                        .withDirectory(new File("logs_"+myId)) //Must be unique
                        .withStorageLevel(StorageLevel.DISK)
                        .build());
        CopycatServer server = builder.build();

        if(myId == 0)
        {
            server.bootstrap().join();
        }
        else
        {
            server.join(addresses).join();
        }
		System.out.println("Log do Disco Recuperado");
		System.out.println("Tamanho da Fila: " + ServerThreadDisk.mapa.size() + "\n");

		System.out.println("Servidor Iniciado...");
		//new Thread(new ServerThreadReceive()).start();
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
						ServerThreadDisk.mapa.put(new BigInteger(String.valueOf(mapa.getChave())), mapa.getTexto());
					else if(mapa.getTipoOperacaoId() == 3)
						ServerThreadDisk.mapa.remove(new BigInteger(String.valueOf(mapa.getChave())));
				}

				sb.append(line).append("\n");
			}

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}

		System.out.println(sb);

	}
}
