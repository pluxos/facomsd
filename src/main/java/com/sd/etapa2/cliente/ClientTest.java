package com.sd.etapa2.cliente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;

import com.google.rpc.Status;
import com.sd.etapa2.grpc.ServerRequestServiceGrpc;
import com.sd.etapa2.grpc.ServerRequest.CreateRequest;
import com.sd.etapa2.grpc.ServerRequest.DeleteRequest;
import com.sd.etapa2.grpc.ServerRequest.DeleteResponse;
import com.sd.etapa2.grpc.ServerRequest.ReadRequest;
import com.sd.etapa2.grpc.ServerRequest.ReadResponse;
import com.sd.etapa2.grpc.ServerRequest.ReadValuesRequest;
import com.sd.etapa2.grpc.ServerRequest.UpdateRequest;
import com.sd.etapa2.grpc.ServerRequestServiceGrpc.ServerRequestServiceBlockingStub;
import com.sd.etapa2.util.Operacao;
import com.sd.etapa2.util.Tipo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


//Classe para testes
//J� exitem alguns arquivos com alguns testes
//Vai ler o arquivo de configura��o do servidor para se conectar
//Caso queira implementar algum teste novo, basta passar o nome do arquivo teste e o ID do servidor como argumento
//O arquivo vai ser todo lido e cada linha vai ser um comando CRUD para executar no servidor
public class ClientTest {

	public static void main(String[] args) {
		System.out.println("---------------------------------------------------------------");
		System.out.println("                            CLIENT TEST");
		System.out.println("---------------------------------------------------------------");

		ManagedChannel channel  = ManagedChannelBuilder.forAddress("localhost", 1230).usePlaintext(true).build();
		ServerRequestServiceBlockingStub  stub = ServerRequestServiceGrpc.newBlockingStub(channel);
	
		try {
			FileReader fileReader = new FileReader(args[0]);
			BufferedReader lerArq = new BufferedReader(fileReader);

			String s;
			String[] linha;
			Tipo tipo;
			int chave;
			String valor;

			while ((s = lerArq.readLine()) != null) {
				
				linha = s.split(" ");
				tipo = Tipo.valueOf(linha[0]);
				
				switch (tipo) {
				case CREATE:

					chave = ( Integer.parseInt(linha[1]) );
					valor = (linha[2]);

					CreateRequest create = CreateRequest.newBuilder().setChave(chave)
							.setValor(valor).build();
					System.out.println("CREATE Chave "+ chave +" "+stub.creat(create).getMensagem());

					break;

				case READ:

					chave = (Integer.parseInt(linha[1]));

					ReadRequest read = ReadRequest.newBuilder().setChave(chave).build();
					ReadResponse readResponse = stub.read(read);

					System.out.println("READ Chave " + chave + " " +readResponse.getMensagem());

					break;

				case UPDATE:

					chave = (Integer.parseInt(linha[1]));
					valor = (linha[2]);

					UpdateRequest update = UpdateRequest.newBuilder().setChave(chave)
							.setValor(valor).build();
					System.out.println("UPDATE Chave "+ chave +" "+stub.update(update).getMensagem());
					break;

				case DELETE:

					chave = (Integer.parseInt(linha[1]));

					DeleteRequest delete = DeleteRequest.newBuilder().setChave(chave).build();
					DeleteResponse deleteResponse = stub.delete(delete);

					System.out.println("DELETE CHAVE "+chave+" "+deleteResponse.getMensagem());
//				break;
				default:
					System.out.println("Comando Invalido.");
					break;
				}

			}
			Operacao sair = new Operacao();
			sair.setTipo(Tipo.SAIR);

			// Sleep para aguardar o servidor finalizar a canexao antes do cliente
			Thread.sleep(2000);

			lerArq.close();

		} catch (NumberFormatException e) {
			System.out.println("Erro " + e.getMessage());
			e.printStackTrace();
		} catch (UnknownHostException e) {
			System.out.println("Erro " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Erro " + e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Erro " + e.getMessage());
			e.printStackTrace();
		}

		System.exit(0);

	}
}
