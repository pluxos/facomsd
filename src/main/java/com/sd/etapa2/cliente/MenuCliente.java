package com.sd.etapa2.cliente;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.Scanner;

import com.sd.etapa2.grpc.ServerRequest.CreateRequest;
import com.sd.etapa2.grpc.ServerRequest.DeleteRequest;
import com.sd.etapa2.grpc.ServerRequest.DeleteResponse;
import com.sd.etapa2.grpc.ServerRequest.ReadRequest;
import com.sd.etapa2.grpc.ServerRequest.ReadResponse;
import com.sd.etapa2.grpc.ServerRequest.ReadValuesRequest;
import com.sd.etapa2.grpc.ServerRequest.SairRequest;
import com.sd.etapa2.grpc.ServerRequest.SairResponse;
import com.sd.etapa2.grpc.ServerRequest.UpdateRequest;
import com.sd.etapa2.grpc.ServerRequestServiceGrpc;
import com.sd.etapa2.grpc.ServerRequestServiceGrpc.ServerRequestServiceBlockingStub;
import com.sd.etapa2.util.Operacao;
import com.sd.etapa2.util.Status;
import com.sd.etapa2.util.Tipo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class MenuCliente {

	private static ManagedChannel channel;
	private static ServerRequestServiceBlockingStub stub;
	public static void main(String[] args) {

		MenuCliente client = new MenuCliente(1230);
		client.rodar();

	}


	public MenuCliente(int porta) {
		
		//Canal de comunicação do cliente usando gRPC
		channel = ManagedChannelBuilder.forAddress("localhost", porta).usePlaintext(true).build();
		stub = ServerRequestServiceGrpc.newBlockingStub(channel);
	}

	public void rodar() {
		System.out.println("---------- Conexao Estabelecida com Sucesso -----------");
		boolean continua = true;
		Scanner scanner = new Scanner(System.in);
		String tipo;
		Operacao operacao = new Operacao();
		

		while (continua) {
			System.out.println("\nEscolha a Operacao:");
			tipo = scanner.next();
			switch (tipo) {
			case "CREATE":

				operacao.setTipo(Tipo.CREATE);
				operacao.setStatus(Status.SOLICITACAO);
				System.out.println("Digite a Chave:");
				operacao.setChave(BigInteger.valueOf(scanner.nextInt()));
				System.out.println("Digite o valor:");
				operacao.setValor(scanner.next());

				CreateRequest create = CreateRequest.newBuilder().setChave(operacao.getChave().intValue())
						.setValor(operacao.getValor()).build();
				System.out.println(stub.creat(create).getMensagem());
				
				break;

			case "READ":

				operacao.setTipo(Tipo.READ);
				operacao.setStatus(Status.SOLICITACAO);
				System.out.println("Digite a Chave:");
				operacao.setChave(BigInteger.valueOf(scanner.nextInt()));

				ReadRequest read = ReadRequest.newBuilder().setChave(operacao.getChave().intValue()).build();
				ReadResponse readResponse = stub.read(read);

				System.out.println(readResponse.getMensagem());

				break;

			case "READVALUES":

				ReadValuesRequest readValueRequest = ReadValuesRequest.newBuilder().build();

				Iterator<ReadResponse> it = stub.readvalues(readValueRequest);
				while (it.hasNext()) {
					ReadResponse elemento = it.next();
					System.out.println(elemento.getMensagem());
				}

				break;

			case "UPDATE":

				operacao.setTipo(Tipo.UPDATE);
				operacao.setStatus(Status.SOLICITACAO);
				System.out.println("Digite a Chave:");
				operacao.setChave(BigInteger.valueOf(scanner.nextInt()));
				System.out.println("Digite o Valor:");
				operacao.setValor(scanner.next());

				UpdateRequest update = UpdateRequest.newBuilder().setChave(operacao.getChave().intValue())
						.setValor(operacao.getValor()).build();
				System.out.println(stub.update(update).getMensagem());
				break;

			case "DELETE":

				operacao.setTipo(Tipo.DELETE);
				operacao.setStatus(Status.SOLICITACAO);
				System.out.println("Digite a Chave:");
				operacao.setChave(BigInteger.valueOf(scanner.nextInt()));

				DeleteRequest delete = DeleteRequest.newBuilder().setChave(operacao.getChave().intValue()).build();
				DeleteResponse deleteResponse = stub.delete(delete);

				System.out.println(deleteResponse.getMensagem());
				break;

			case "SAIR":

				operacao.setTipo(Tipo.SAIR);
				System.out.println();
				SairRequest sair = SairRequest.newBuilder().build();
				SairResponse sairResponse = stub.sair(sair);
				System.out.println(sairResponse.getMensagem());
				scanner.close();

				continua = false;
				break;

			case "AJUDA":

				System.out.println("\nCREATE - Inserir no Banco \nDeve ser Informado a chave e o valor do elemento");
				System.out.println("\nREAD - Ler uma instancia \nDeve ser Informada a chave do elemento");
				System.out.println("\nREAD - Lista todos os elementos do node atual");
				System.out.println("\nUPDATE - Atualizar uma Instancia do Banco \nDeve ser informado chave e valor");
				System.out.println("\nDELETE - Apagar uma Instancia do Banco \nDeve ser informado a chave do elemento");
				System.out.println("\nSAIR - Desconectar do Banco");

				break;
			default:
				System.out.println("\nOperacao Invalida - Digite AJUDA para mostrar todas as operacoes");
			}
		}
		channel.shutdown();
	}
}
