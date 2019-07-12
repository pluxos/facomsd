package com.sd.projeto1.main;

import com.sd.projeto1.command.AddCommand;
import com.sd.projeto1.command.DeleteCommand;
import com.sd.projeto1.command.GetCommand;
import com.sd.projeto1.command.PutCommand;
import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.util.FileUtils;
import com.sd.projeto1.util.Utils;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Server extends StateMachine {


	private static Map<Long, String> maps = new HashMap<>();

	public String AddCommand(Commit<AddCommand> commit) {
		try{
			AddCommand aec = commit.operation();

			if (maps.containsKey(aec.id)) {

				return "Mensagem com essa chave já adicionada";
			}

			maps.put(aec.id,aec.message);

            FileUtils.writeFile(String.valueOf(Utils.CREATE), aec.id, aec.message);

			return "Mensagem adicionada com sucesso!";
		}finally{
			commit.close();
		}
	}

	public String GetCommand(Commit<GetCommand> commit){
		try{
			GetCommand aec = commit.operation();
			String message = maps.get(aec.id);

			if (StringUtils.isBlank(message))
				return "Dados não encontrados para a chave: " + aec.id;

			return "Chave encontrada: Chave: " + aec.id + " Messagem: " + message;
		}finally{
			commit.close();
		}
	}

	public String PutCommand(Commit<PutCommand> commit) {
		try {
			PutCommand aec = commit.operation();

			if (!maps.containsKey(aec.id)) {

				return "Mensagem com essa chave não existe";
			}

			maps.put(aec.id, aec.message);

            FileUtils.writeFile(String.valueOf(Utils.UPDATE), aec.id, aec.message);

			return "Mensagem atualizada com sucesso!";
		} finally {
			commit.close();
		}
	}

	public String DeleteCommand(Commit<DeleteCommand> commit){
		try{
			DeleteCommand aec = commit.operation();

			if (!maps.containsKey(aec.id)) {

				return "Chave " +aec.id+" não encontrada";
			}

			maps.remove(aec.id);

            FileUtils.writeFile(String.valueOf(Utils.DELETE), aec.id, "");

			return "Chave " +aec.id+" excluida com sucesso!";
		}finally{
			commit.close();
		}
	}

	public static void main(String[] args) throws Exception {
		List<Mapa> logs = new ArrayList<Mapa>();




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
			loadData();
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
        int operationId = 0;
        Long key = 0L;
        String message = "";

		try (BufferedReader br = Files.newBufferedReader(Paths.get("app.log"))) {

			String line;
			while ((line = br.readLine()) != null) {
				String[] content = line.split("#");
				if(content.length>0){
                    operationId = Integer.parseInt(content[0]);
                    key = Long.valueOf(content[1]);
					if(content.length>2)
                        message = content[2];
                    if (operationId == 1 || operationId == 2)
                        maps.put(key, message);
                    else if (operationId == 3)
                        maps.remove(key);
				}

				sb.append(line).append("\n");
			}

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		System.out.println(sb);
	}
}
