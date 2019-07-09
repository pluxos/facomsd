package cliente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;

import util.Operacao;
import util.Status;
import util.Tipo;



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

		Socket socket;
		try {			
			FileReader fileReader = new FileReader(args[0]);
			BufferedReader lerArq = new BufferedReader(fileReader);

			socket = new Socket("localhost", 9876);
			ObjectOutputStream output = null;
			ObjectInputStream input;
			Operacao reply;
			String s;
			String[] linha;
			Tipo tipo;
			BigInteger chave;
			String valor;
			
			while ((s = lerArq.readLine()) != null) {
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());
				
				linha = s.split(" ");
				tipo = Tipo.valueOf(linha[0]);

				switch (tipo) {
				case CREATE:

					try {

						chave = (BigInteger.valueOf(Integer.parseInt(linha[1])));
						valor = (linha[2]);

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
						System.out.print("READ Status " + reply.getStatus());
						if (reply.getStatus() == Status.OK) {
							System.out.print(" "+reply.getMensagem());
						} else {
							System.out.print(" chave "+chave);
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
						valor = (linha[2]);

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
			Operacao sair = new Operacao();
			sair.setTipo(Tipo.SAIR);
			output.writeObject(sair);
			output.flush();

			// Sleep para aguardar o servidor finalizar a canexao antes do cliente
			Thread.sleep(2000);

			socket.close();
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
