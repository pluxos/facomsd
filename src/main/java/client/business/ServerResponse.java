package client.business;

import client.commons.domain.User;
import client.commons.utils.DataCodificator;
import org.apache.commons.lang3.ObjectUtils;
import server.commons.domain.GenericResponse;
import server.commons.utils.JsonUtils;
import java.util.Scanner;


public class ServerResponse implements Runnable {

	private Scanner input;

	public ServerResponse(Scanner input){
		this.input = input;
	}

	@Override
	public void run() {
		System.out.println("Respostas do servidor startado!");

		while(true){
			try {

				String res = this.input.nextLine();
				GenericResponse object = JsonUtils.deserialize(res, GenericResponse.class);

				if(!object.getMsg().trim().equals(""))
					System.out.println("Mensagem: " + object.getMsg());
				else
					System.out.println("Sem mensagem");
				System.out.println("esta aqui");
				if (object.getData() != null) {
					User user = DataCodificator.decode(object.getData());
					System.out.println("Nome: " + user.getName());
					System.out.println("Email: " + user.getEmail());
					System.out.println("Senha: " + user.getPassword());

				}

			}catch (Exception e){
				System.out.println(e);
				break;
			}
		}
	}

}
