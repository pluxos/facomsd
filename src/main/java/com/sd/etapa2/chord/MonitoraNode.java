package com.sd.etapa2.chord;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//Estabelecer conexao com os nos e monitorar caso algum caia
public class MonitoraNode implements Runnable {

	private int nodeId;
	private Chord chord;
	ObjectOutputStream output;
	ObjectInputStream input;

	public MonitoraNode(Chord chord, ObjectOutputStream output, ObjectInputStream input, int nodeId) {
		this.chord = chord;
		this.output = output;
		this.input = input;
		this.nodeId = nodeId;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.currentThread().sleep(15000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				output.writeInt(1);
				output.flush();
				
				
				output.writeObject(chord.getNode(nodeId).getAntecessores());
				output.flush();
				
				output.writeObject(chord.getNode(nodeId).getFingerTable());
				output.flush();

				output.writeObject(chord.getTabelaPortas());
				output.flush();

			} catch (IOException e) {
				System.out.println("Erro de Comunicacao com Servidor " + nodeId);
				chord.removeNode(nodeId);
				break;
			}
		}
	}
}
