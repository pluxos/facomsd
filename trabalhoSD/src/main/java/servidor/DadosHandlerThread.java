package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Map;

public class DadosHandlerThread extends Thread {
	private Socket socket = null;
	private String comando;
	private int tipo;

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
				tipo = ComandQuery.getTipoComando(comando);

				switch (tipo) {
				case 1:// create
					out.writeObject(Data.create(ComandQuery.getKey(comando), ComandQuery.getValue(comando)));
					break;

				case 2:// read
					out.writeObject(Data.read(ComandQuery.getKey(comando)));
					break;

				case 3:// update
					out.writeObject(Data.update(ComandQuery.getKey(comando), ComandQuery.getValue(comando)));
					break;

				case 4:// delete
					out.writeObject(Data.delete(ComandQuery.getKey(comando)));
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
