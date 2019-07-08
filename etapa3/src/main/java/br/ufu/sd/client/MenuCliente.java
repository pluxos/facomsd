package br.ufu.sd.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import br.ufu.sd.util.Comando;
import br.ufu.sd.util.Operacao;

public class MenuCliente {

	private int porta;
	private String host;
	private Socket socket;

	public MenuCliente(String host,int porta) {
		this.porta = porta;
		this.host = host;
	}

	public void startClient() {

		try {

			socket = new Socket(host, porta);
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

			System.out.println("---------- Conexao Estabelecida com Sucesso -----------");

			boolean continua = true;
			Scanner scanner = new Scanner(System.in);
			String tipo;
			Operacao operacao;

			BigInteger chave;
			byte[] valor;

			Thread threadRespostas = new Thread(new RespostaServer(input));
			threadRespostas.setDaemon(true);
			threadRespostas.start();

			System.out.println("\nEscolha a Operacao:");
			while (continua) {
				tipo = scanner.next();
				switch (tipo) {
				case "CREATE":

					try {

						System.out.println("Digite a Chave:");
						chave = (BigInteger.valueOf(scanner.nextInt()));
						System.out.println("Digite o valor:");
						valor = (scanner.next().getBytes());

						operacao = new Operacao(Comando.CREATE);
						operacao.setChave(chave);
						operacao.setValor(valor);

						output.writeObject(operacao);
						output.flush();
						break;
					} catch (java.util.InputMismatchException e) {
						System.out.println("Chave deve ser um numero inteiro");
						break;
					}

				case "READ":

					System.out.println("Digite a Chave:");
					try {
						chave = (BigInteger.valueOf(scanner.nextInt()));
						operacao = new Operacao(Comando.READ);
						operacao.setChave(chave);
						
						output.writeObject(operacao);
						output.flush();

						break;
					} catch (java.util.InputMismatchException e) {
						System.out.println("Chave deve ser um numero inteiro");
						break;
					}

				case "UPDATE":

					try {
						System.out.println("Digite a Chave:");
						chave = (BigInteger.valueOf(scanner.nextInt()));
						System.out.println("Digite o Valor:");
						valor = (scanner.next().getBytes());

						operacao = new Operacao(Comando.UPDATE);
						operacao.setChave(chave);
						operacao.setValor(valor);

						output.writeObject(operacao);
						output.flush();

						break;
					} catch (java.util.InputMismatchException e) {
						System.out.println("Chave deve ser um numero inteiro");
						break;
					}

				case "DELETE":

					try {
						System.out.println("Digite a Chave:");
						chave = (BigInteger.valueOf(scanner.nextInt()));

						operacao = new Operacao(Comando.DELETE);
						operacao.setChave(chave);

						output.writeObject(operacao);
						output.flush();

						break;

					} catch (java.util.InputMismatchException e) {
						System.out.println("Chave deve ser um numero inteiro");
						break;
					}

				case "SAIR":

					operacao = new Operacao(Comando.SAIR);;

					output.writeObject(operacao);
					output.flush();

					try {
						System.out.println("Aguardando novas respostas...");
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						System.out.println("Erro "+e.getMessage());
					} 
					scanner.close();
					continua = false;
					break;

				case "AJUDA":

					System.out
							.println("\nCREATE - Inserir no Banco \nDeve ser Informado a chave e o valor do elemento");
					System.out.println("\nREAD - Ler uma instancia \nDeve ser Informada a chave do elemento");
					System.out
							.println("\nUPDATE - Atualizar uma Instancia do Banco \nDeve ser informado chave e valor");
					System.out.println(
							"\nDELETE - Apagar uma Instancia do Banco \nDeve ser informado a chave do elemento");
					System.out.println("\nSAIR - Desconectar do Banco");

					break;
				default:
					System.out.println("\nOperacao Invalida - Digite AJUDA para mostrar todas as operacoes");
				}
			}

		} catch (UnknownHostException e) {
			System.out.println("Erro "+e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro "+e.getMessage());
		}
	}
}
