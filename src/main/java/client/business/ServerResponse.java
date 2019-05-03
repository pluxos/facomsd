package client.business;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import client.commons.domain.User;
import client.commons.utils.DataCodificator;
import client.commons.utils.SocketConnection;
import server.commons.domain.GenericResponse;
import server.commons.exceptions.ServerException;
import server.commons.utils.JsonUtils;

public class ServerResponse implements Runnable {

	private Scanner input;

	public ServerResponse(Scanner input) {
		this.input = input;
	}

	@Override
	public void run() {
		System.out.println("Respostas do servidor startado!");

		while (true) {
			try {
				String res = this.input.nextLine();
				GenericResponse object = JsonUtils.deserialize(res, GenericResponse.class);

				if (!object.getMsg().trim().equals(""))
					System.out.println("Mensagem: " + object.getMsg());
				else
					System.out.println("Sem mensagem");

				if (object.getData() != null) {
					User user = DataCodificator.decode(object.getData());
					System.out.println("Nome: " + user.getName());
					System.out.println("Email: " + user.getEmail());
					System.out.println("Senha: " + user.getPassword());
				}
				System.out.println();
			} catch (NoSuchElementException e) {
				System.out.println("Infelizmente a comunicação com o servidor foi interrompida");
				if (!SocketConnection.isAlive()) {
					System.exit(1);
				}
				return;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServerException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}