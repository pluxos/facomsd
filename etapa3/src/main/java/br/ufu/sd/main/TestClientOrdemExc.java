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

//classe para testar ordem de execucao, basta passar ip e porta do server
public class TestClientOrdemExc {
	public static void main(String[] args) {

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

			socket = new Socket(ip, porta);
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			OperacaoReply reply;

			System.out.println("---------------------------------------------------------------------------");
			System.out.println("                                ORDEM DE EXECUCAO");
			System.out.println("---------------------------------------------------------------------------");

			// Esse teste vai comecar pela chave 1000 e vai ate a 2001
			//Essa alteracao na chave foi necessaria para nao ser influenciada pelos outros tests
			BigInteger a = BigInteger.valueOf(1000);
			Operacoes.testCREATE(output, input, a, String.valueOf(1).getBytes());
			int v;
			for (int i = 1; i <= 1000; i++) {

				reply = Operacoes.testREAD(output, input, a);
				v = Integer.valueOf(ByteString.copyFrom(reply.getValor()).toStringUtf8());
				v++;
				a = a.add(BigInteger.valueOf(1));
				Operacoes.testCREATE(output, input, a, String.valueOf(v).getBytes());

			}

			System.out.println("READ CHAVE - "+a.intValue());
			reply = Operacoes.testREAD(output, input, BigInteger.valueOf(a.intValue()));
			System.out.print(reply.getStatus());
			if (reply.getStatus() == StatusResponse.OK) {
				System.out.print(" - VALOR: " + ByteString.copyFrom(reply.getValor()).toStringUtf8());
			}
			System.out.println();

			Operacao operacao = new Operacao(Comando.SAIR);
			output.writeObject(operacao);

			Thread.sleep(2000);

			socket.close();


		} catch (NumberFormatException e) {
			System.out.println("Erro " + e.getMessage());
		} catch (UnknownHostException e) {
			System.out.println("Erro " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Erro " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Erro " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("Erro " + e.getMessage());
		}

		System.exit(0);

	}
}
