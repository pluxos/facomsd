package br.ufu.sd.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.protobuf.ByteString;

import br.ufu.sd.util.Comando;
import br.ufu.sd.util.Operacao;
import br.ufu.sd.util.OperacaoReply;
import br.ufu.sd.util.StatusResponse;

//Classe para testes
//Já exitem alguns arquivos com alguns testes
//Vai ler o arquivo de configuração do servidor para se conectar
//Caso queira implementar algum teste novo, basta passar o nome do arquivo teste e o ID do servidor como argumento
//O arquivo vai ser todo lido e cada linha vai ser um comando CRUD para executar no servidor
public class ClientTest {

	public static void main(String[] args) {
		System.out.println("---------------------------------------------------------------");
		System.out.println("                            CLIENT TEST");
		System.out.println("---------------------------------------------------------------");

		Socket socket;
		try {

			FileReader fileReader = new FileReader("cluster-config.txt");
			BufferedReader lerArq = new BufferedReader(fileReader);

			int id = Integer.parseInt(args[0]);
			String ip = null;
			int porta = 0;
			
			String s;
			String[] linha;
			
			while ((s = lerArq.readLine()) != null) {
				linha = s.split(" ");
				if (id == Integer.parseInt(((linha[0]).split("="))[1])) {
					ip = ((linha[1]).split("="))[1];
					porta = Integer.parseInt(((linha[3]).split("="))[1]);
					break;
				}
			}

			lerArq.close();
			fileReader.close();
			
			fileReader = new FileReader(args[1]);
			lerArq = new BufferedReader(fileReader);

			socket = new Socket(ip, porta);
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

			Comando tipo;
			BigInteger chave;
			byte[] valor;
			OperacaoReply reply;
			while ((s = lerArq.readLine()) != null) {

				linha = s.split(" ");
				tipo = Comando.valueOf(linha[0]);

				switch (tipo) {
				case CREATE:

					try {

						chave = (BigInteger.valueOf(Integer.parseInt(linha[1])));
						valor = (linha[2].getBytes());

						reply = Operacoes.testCREATE(output, input, chave, valor);
						System.out.println("CREATE chave " + chave.longValue() + " Status " + reply.getStatus());

						break;
					} catch (java.util.InputMismatchException e) {
						System.out.println("Chave deve ser um numero inteiro");
						break;
					} catch (ClassNotFoundException e) {
						System.out.println("Erro " + e.getMessage());
						break;
					}

				case READ:

					try {
						chave = (BigInteger.valueOf(Integer.parseInt(linha[1])));

						reply = Operacoes.testREAD(output, input, chave);
						System.out.print("READ chave " + chave.longValue() + " Status " + reply.getStatus());
						if (reply.getStatus() == StatusResponse.OK) {
							System.out.print(" - VALOR: " + ByteString.copyFrom(reply.getValor()).toStringUtf8());
						}
						System.out.println();

						break;
					} catch (java.util.InputMismatchException e) {
						System.out.println("Chave deve ser um numero inteiro");
						break;
					} catch (ClassNotFoundException e) {
						System.out.println("Erro " + e.getMessage());
						break;
					}

				case UPDATE:

					try {
						chave = (BigInteger.valueOf(Integer.parseInt(linha[1])));
						valor = (linha[2].getBytes());

						reply = Operacoes.testUPDATE(output, input, chave, valor);
						System.out.println("UPDATE chave " + chave.longValue() + " Status " + reply.getStatus());

						break;
					} catch (java.util.InputMismatchException e) {
						System.out.println("Chave deve ser um numero inteiro");
						break;
					} catch (ClassNotFoundException e) {
						System.out.println("Erro " + e.getMessage());
					}

				case DELETE:

					try {
						chave = (BigInteger.valueOf(Integer.parseInt(linha[1])));

						reply = Operacoes.testDELETE(output, input, chave);
						System.out.println("DELETE chave " + chave.longValue() + " Status " + reply.getStatus());

						break;

					} catch (java.util.InputMismatchException e) {
						System.out.println("Chave deve ser um numero inteiro");
						break;
					} catch (ClassNotFoundException e) {
						System.out.println("Erro " + e.getMessage());
					}
				default:
					System.out.println("Comando Invalido.");
					break;
				}

			}
			output.writeObject(new Operacao(Comando.SAIR));
			output.flush();

			// Sleep para aguardar o servidor finalizar a canexao antes do cliente
			Thread.sleep(2000);

			socket.close();
			lerArq.close();
						
		} catch (NumberFormatException e) {
			System.out.println("Erro " + e.getMessage());
		} catch (UnknownHostException e) {
			System.out.println("Erro " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("Erro " + e.getMessage());
		}
		
		System.exit(0);

	}
}
