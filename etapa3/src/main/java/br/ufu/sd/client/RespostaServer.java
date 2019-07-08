package br.ufu.sd.client;

import java.io.IOException;
import java.io.ObjectInputStream;

import com.google.protobuf.ByteString;

import br.ufu.sd.util.Comando;
import br.ufu.sd.util.OperacaoReply;
import br.ufu.sd.util.StatusResponse;

public class RespostaServer implements Runnable {

	private ObjectInputStream input;


	public RespostaServer(ObjectInputStream input) {
		this.input = input;
	}

	@Override
	public void run() {
		OperacaoReply reply;
		//ByteString para auxiliar a conversao de string para byte[] e vice-versa
		ByteString value;
		while (true) {

			try {
				reply = (OperacaoReply) input.readObject();

				System.out.print(reply.getComando() + " - " + reply.getStatus());
				
				if(reply.getComando() == (Comando.READ) && reply.getStatus() == StatusResponse.OK) {
					value = ByteString.copyFrom(reply.getValor());
					System.out.print(" - Valor: " +value.toStringUtf8());
					
				}
				System.out.println();

			} catch (ClassNotFoundException e) {
				
				System.out.println("Erro " + e.getMessage());
				break;
			} catch (IOException e) {
				System.out.println("Erro " + e.getMessage());
				break;
			}
		}
	}
}
