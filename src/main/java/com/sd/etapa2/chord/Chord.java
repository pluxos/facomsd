package com.sd.etapa2.chord;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sd.etapa2.util.Operacao;

//Processo chord que coordena os servidores
//Nao completo
public final class Chord {

	private Vector<Node> anel;
	private int m;
	private int maxNodes;
	private ServerSocket serverSocket;
	private ExecutorService threadPool;
	private HashMap<Integer, Integer> tabelaPortas;

	public Chord(int m, int porta) {
		this.maxNodes = (int) Math.pow(2, m);
		this.m = m;
		anel = new Vector<>();
		threadPool = Executors.newCachedThreadPool();
		tabelaPortas = new HashMap<>();
		try {
			serverSocket = new ServerSocket(porta);
		} catch (IOException e) {
			System.out.println("Nao foi possivel iniciar o chord");
		}
	}

	public void iniciaChord() {

		while (true) {
			try {

				Socket socket = serverSocket.accept();
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

				
				int id = input.readInt();

				Node node = new Node(id, getM());
				node.setPorta(input.readInt());
				insereNode(node);
				System.out.println("Servidor Conectado");
				System.out.println("ID: " + node.getId() + " Porta: " + node.getPorta());

				MonitoraNode monitoraNode = new MonitoraNode(this, output,input,id);
				threadPool.execute(monitoraNode);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public synchronized void insereNode(Node node) {
		if (node.getId() > maxNodes - 1) {
			System.out.println("Nao é possível criar um node com chave maior que " + (maxNodes - 1));
			return;
		}
		if (anel.contains(node)) {
			System.out.println("Node já existe");
			return;
		}
		if (anel.size() >= maxNodes) {
			System.out.println("Anel cheio");
			return;
		}

		tabelaPortas.put(node.getId(), node.getPorta());
		anel.add(node);
		ordenaAnel();
		ajustaAntecessoresInsercao(node);
		criaFingerTable();
	}

	public synchronized void ajustaAntecessoresInsercao(Node node) {

		// Caso tenha mais de uma nó no anel

		if (anel.size() > 1) {
			Integer anterior = node.getId();
			Node nodeAnterior = new Node(anterior, m);
			// Pegar o nó anterior
			while (true) {
				anterior--;
				if (anterior < 0) {
					anterior += maxNodes;
				}
				nodeAnterior.setId(anterior);
				if (anel.contains(nodeAnterior)) {
					break;
				}
			}
			// i vai assumir os valores dos antecessores para ser inseriodo no nó
			int i = node.getId();
			while (true) {
				// se i for igual o id do anterior significa que chegou no nó anterior e
				// terminou a inserção dos antecessores
				if (i == anterior) {
					break;
				}
				// inserir os antecessores no nó atual
				node.insereAntecessor(i);
				// proximo antecessor
				i--;
				// caso i assuma valor negativo ele é ajustado para ultima posição do anel
				if (i < 0) {
					i += maxNodes;
				}
			}

			Integer prox = node.getId();
			Node nodeProx = new Node(prox, m);
			// pega o proximo nó
			while (true) {
				prox++;
				if (prox == maxNodes) {
					prox -= maxNodes;
				}
				nodeProx.setId(prox);
				if (anel.contains(nodeProx)) {
					break;
				}
			}

			// reajustar o i para o index do proximo elemento
			i = 0;
			for (Node node2 : anel) {
				if (node2.getId() == nodeProx.getId()) {
					break;
				}
				i++;
			}

			nodeProx = anel.get(i);

			// removendo antecessores do próximo nó
			for (Integer antecessor : node.getAntecessores()) {
				if (nodeProx.possuiAntecessor(antecessor)) {
//					if (prox.possuiElemento(antecessor)) {
//						node.insereElemento(antecessor, prox.getElemento(antecessor));
//						prox.removeElemento(antecessor);
//					}
					nodeProx.removeAntecessor(antecessor);
				}
			}
		} else { // caso tenha apenas uma nó no anel, todos os valores vão ser seu antecessor
			for (int i = 0; i < maxNodes; i++) {
				node.insereAntecessor(i);
			}
		}
	}

	public synchronized void removeNode(int id) {

		int atual = 0;
		int prox = 0;
		int ant = 0;
		tabelaPortas.remove(id);
		while (true) {
			if (id == anel.get(atual).getId()) {
				prox = atual + 1;
				if (prox >= anel.size()) {
					prox = 0;
				}
				break;
			}
			atual++;
		}
		ant = atual - 1;
		if (ant < 0) {
			ant += anel.size();
		}

		int i = id;
		System.out.println(atual + " " + prox + " " + ant);
		while (true) {
			anel.get(prox).insereAntecessor(i);
			i--;
			if (i < 0) {
				i += maxNodes;
			}
			if (i == anel.get(ant).getId()) {
				break;
			}
		}

		anel.remove(atual);
		criaFingerTable();
	}

	public void criaFingerTable() {

		limpaFingerTable();
		for (Node node : anel) {
			for (int j = 0; j < m; j++) {
				Integer calculoNode = node.getId() + (int) Math.pow(2, (j));
				calculoNode = calculoNode % maxNodes;
				Node node2 = new Node(calculoNode, m);
				while (!anel.contains(node2)) {
					calculoNode++;
					if (calculoNode > maxNodes) {
						calculoNode = 0;
					}
					node2.setId(calculoNode);
				}
				node.fingerTable(calculoNode, j);
			}
		}
	}

	public void limpaFingerTable() {
		for (Node node : anel) {
			node.limpaFingerTable();
		}
	}

	public void imprimeFingerTable() {
		for (Node node : anel) {
			node.imprimeFingerTable();
		}
	}

//

	public void imprimeAntecessores() {
		for (Node node : anel) {
			node.imprimeAntecessores();
		}
	}

	public int getM() {
		return m;
	}

	private void ordenaAnel() {
		Collections.sort(anel, new Comparator() {
			public int compare(Object o1, Object o2) {
				Node a = (Node) o1;
				Node b = (Node) o2;

				return a.getId() - b.getId();
			}
		});
	}

	public Node getNode(int nodeId) {
		for (Node node : anel) {
			if (node.getId() == nodeId) {
				return node;
			}
		}
		return null;
	}

	public Integer proxResponsavel(Operacao operacao, Integer responsavel) {
		Node nodeResponsavel = null;
		int i;
		for (Node node : anel) {
			if (node.getId() == responsavel) {
				nodeResponsavel = node;
				break;
			}
		}
		Integer[] fingerTable = nodeResponsavel.getFingerTable();
		Integer chave = operacao.getChave().intValue();
		for (i = 0; i < m - 1; i++) {

			if (fingerTable[i] >= chave) {
				responsavel = fingerTable[i];
				break;
			}
			if (fingerTable[i + 1] > chave) {
				responsavel = fingerTable[i];
				break;
			}
		}
		if (i == m - 1) {
			responsavel = fingerTable[i];
		}
		return responsavel;
	}

	public HashMap<Integer, Integer> getTabelaPortas() {
		return this.tabelaPortas;
	}
}
