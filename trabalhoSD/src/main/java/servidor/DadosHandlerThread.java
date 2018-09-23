package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Map;

public class DadosHandlerThread extends Thread {
	private Socket socket = null;
	private String comando, value;
	private BigInteger key;
	int tipo;

	// Map<BigInteger, String> dados;

	// public DadosHandlerThread(Socket socket,Map<BigInteger, String> dados) {
	public DadosHandlerThread(Socket socket) {
		this.socket = socket;
		// this.dados = dados;

		// System.out.println("dados setados " + this.dados.toString());
	}

	public void run() {
		try {
			System.out.println("Cliente conectado");
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			out.writeObject(Menu.operacoes);

			while (true) {
				comando = (String) in.readObject();
				System.out.println("Executando: " + comando);
				String stringtipo = comando.split(" ")[0];
				stringtipo.toLowerCase();
				tipo = -1;
				if (stringtipo.equals("create")) {
					tipo = 1;
				} else {
					if (stringtipo.equals("read")) {
						tipo = 2;
					} else {
						if (stringtipo.equals("update")) {
							tipo = 3;
						} else {
							if (stringtipo.equals("delete")) {
								tipo = 4;
							}
						}
					}
				}

				if (stringtipo.equals("1") || stringtipo.equals("2") || stringtipo.equals("3")
						|| stringtipo.equals("4")) {
					tipo = Integer.parseInt(stringtipo);
				}

				switch (tipo) {
				case 1:// create
					key = new BigInteger((comando.split(":")[0]).split(" ")[1]);
					value = comando.split(":")[1];
					out.writeObject(Data.create(key, value));
					break;

				case 2:// read
					key = new BigInteger(comando.split(" ")[1]);
					out.writeObject(Data.read(key));
					break;

				case 3:// update
					key = new BigInteger((comando.split(":")[0]).split(" ")[1]);
					value = comando.split(":")[1];
					out.writeObject(Data.update(key, value));
					break;

				case 4:// delete
					key = new BigInteger(comando.split(" ")[1]);
					out.writeObject(Data.delete(key));
					break;

				default:
					out.writeObject("Erro!");
					break;
				}

			}
//			out.close();
//			in.close();
//			socket.close();
		} catch (Exception e) {
		}
	}

}
