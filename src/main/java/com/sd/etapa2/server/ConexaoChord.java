package com.sd.etapa2.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Vector;

import com.sd.etapa2.chord.Node;


//Runnable responsável por receber as notificão do chord
//Não completa
public class ConexaoChord implements Runnable {

	private ServerNode server;
	private Socket socket;

	public ConexaoChord(ServerNode server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}

	@Override
	public void run() {

		try {
			socket = new Socket("localhost", 5050);
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			output.writeInt(server.getId());
			output.flush();
			output.writeInt(server.getPorta());
			output.flush();
			Node nodeAt = null;
			while (true) {
				input.readInt();

				output.writeInt(1);
				output.flush();

				System.out.println("Atualizando Server");

				server.setAntecessores((Vector<Integer>)input.readObject());
				server.setFingerTable((Integer[])input.readObject());
				
				System.out.println("Antecessore ");
				for (Integer integer : server.getAntecessores()) {
					System.out.println(" "+integer);
				}

				
				HashMap<Integer, Integer> map = (HashMap<Integer, Integer>) input.readObject();
				
				
//				server.setTabelaPorta((HashMap<Integer, Integer>) input.readObject());

			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}