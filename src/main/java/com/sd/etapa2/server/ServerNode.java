package com.sd.etapa2.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.sd.etapa2.grpc.ServerRequestServiceImpl;
import com.sd.etapa2.util.Banco;
import com.sd.etapa2.util.Operacao;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class ServerNode implements Comparable<ServerNode> {
	private static Banco banco = new Banco(); // Inicializando o banco
	private ServerSocket servidorSocket;
	private ExecutorService threadPool;
	private AtomicBoolean estaRodando;
	private ArrayBlockingQueue<Operacao> filaComandos;
	private ArrayBlockingQueue<Operacao> filaBanco;
	private ArrayBlockingQueue<Operacao> filaLogs;
	private ArrayBlockingQueue<Operacao> filaRedirecionamento;
	private String nomeArquivo;
	private int ID;
	private Integer[] fingerTable;
	private Vector<Integer> antecessores;
	private HashMap<Integer, Integer> tabelaPortas;
	//Socket para conectar com o chord
//	private Socket chordSocket;
	private int porta;
	
	private ServerRequestServiceImpl srs;
	private Server servidor;

	public ServerNode(int ID, int porta, String nomeDoArquivo) throws IOException {
		System.out.println("----- Iniciando Servidor -----");
		this.threadPool = Executors.newCachedThreadPool(); // Pool de Threads do servidor
		this.estaRodando = new AtomicBoolean(true);
		filaComandos = new ArrayBlockingQueue<>(10);
		filaBanco = new ArrayBlockingQueue<>(10);
		filaLogs = new ArrayBlockingQueue<>(10);
		//Fila para Redirecionar as Requisições para outro servidor
//		filaRedirecionamento = new ArrayBlockingQueue<>(10);
		nomeArquivo = nomeDoArquivo;
		this.ID = ID;
		this.porta = porta;
		//Iniciando servidor gRPC
		srs = new ServerRequestServiceImpl(banco, filaComandos/*, filaRedirecionamento*/);
		servidor = ServerBuilder.forPort(porta).addService(srs).build(); 

	}

	public void rodar() {

		try {
			
			//Thread que monitorar mudanças na rede
			//A Runnable não está implementada
//			ConexaoChord conexaoChord = new ConexaoChord(this, chordSocket);
//			threadPool.execute(conexaoChord);
			
			servidor.start();

			povoaBanco();
			
			Consumer consumer = new Consumer(filaComandos, filaBanco, filaLogs);
			threadPool.execute(consumer);

			InsereBanco insereBanco = new InsereBanco(banco, filaBanco);
			threadPool.execute(insereBanco);

			InsereLog insereLog = new InsereLog(nomeArquivo, filaLogs,banco);
			threadPool.execute(insereLog);
			
			System.out.println("Ininicando Servidor " + ID);
			servidor.awaitTermination();
		} catch (IOException e) {
			System.out.println("Erro ao iniciar o servidor");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Erro ao fechar o servidor");
			e.printStackTrace();
		}

	}

	public void povoaBanco() {

		List<String[]> lista = new ArrayList<>();

		//ler o Snapsthot
		
		try {
			FileReader fr = new FileReader("Snapshot"+nomeArquivo);
			BufferedReader br = new BufferedReader(fr);
			String str;
			while ((str = br.readLine()) != null) {
				lista.add(str.split(","));
			}
			br.close();
			
			for (String[] elemento : lista) {
				banco.setValor(new BigInteger(elemento[0]), elemento[1]);
			}
			
			lista.clear();
		} catch (FileNotFoundException e1) {
			System.out.println("Snapshot não encontrado");
		} catch (IOException e) {
			System.out.println("Arquivo vazio");
		}
		
		//ler os logs
		for(int i = 1; i < 4; i++) {
			try {
				String str;
				FileReader fr = new FileReader(i+"log"+nomeArquivo);
				BufferedReader br = new BufferedReader(fr);
				while ((str = br.readLine()) != null) {
					lista.add(str.split(","));
				}
				br.close();
				for (String[] elemento : lista) {
					switch (elemento[0]) {
					case "CREATE":
						banco.setValor(new BigInteger(elemento[1]), elemento[2]);
						break;
						
					case "DELETE":
						banco.deletaValor(new BigInteger(elemento[1]));
						break;
						
					case "UPDATE":
						banco.atualizaValor(new BigInteger(elemento[1]), elemento[2]);
						break;
					default:
						break;
					}
				}
				
			} catch (IOException e) {
				System.out.println("Arquivo não encontrado!");
			}
			
		}
		
	}

	public void parar() throws IOException {
		estaRodando.set(false);
		servidorSocket.close();
	}

	public boolean possuiElemento(Integer chave) {
		return banco.existeElemento(BigInteger.valueOf((chave)));
	}

	public String getElemento(Integer chave) {
		return banco.getValor(BigInteger.valueOf(chave));
	}

	public void insereElemento(Integer chave, String valor) {
		banco.setValor(BigInteger.valueOf(chave), valor);
	}

	public void removeElemento(Integer chave) {
		banco.deletaValor(BigInteger.valueOf(chave));

	}

	public void setAntecessores(Vector<Integer> antecessores) {
		srs.setAntecessores(antecessores);
		this.antecessores = antecessores;
	}

	public void setFingerTable(Integer[] fingerTable) {
		this.fingerTable = fingerTable;
	}

	public void imprimeAntecessores() {
		System.out.print("Node: " + ID + " Antecessores: [");
		for (Integer antecessor : antecessores) {
			System.out.print(antecessor + " ");
		}
		System.out.print("]");
	}

	public Vector<Integer> getAntecessores() {
		return this.antecessores;
	}

	public int getId() {
		return ID;
	}

	public void imprimeFingerTable() {
		System.out.println("Node ID: " + this.ID);
		for (int i = 0; i < fingerTable.length; i++) {
			System.out.println("ID " + (i + 1) + " - Sucessor " + fingerTable[i]);
		}
	}

	public boolean possuiAntecessor(Integer antecessor) {
		if (antecessores.contains(antecessor)) {
			return true;
		} else
			return false;
	}

	public Integer[] getFingerTable() {
		return this.fingerTable;
	}

	public void imprimeElementos() {
		for (String elemento : banco.getElementos()) {
			System.out.println(elemento);
		}
	}

	@Override
	public boolean equals(Object obj) {
		ServerNode node = (ServerNode) obj;
		System.out.println(node.getId());
		System.out.println(ID);
		if (node.getId() == this.ID) {
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(ServerNode node) {
		return ID - node.getId();
	}

	public boolean ehResponsavel(Integer chave) {
		return antecessores.contains(chave);
	}

	public void setTabelaPorta(HashMap<Integer, Integer> tabelaPortas) {
		this.tabelaPortas = tabelaPortas;

	}

	public void imprimeTabelaPortas() {
		for (Entry<Integer, Integer> linha : tabelaPortas.entrySet()) {
			System.out.println("ID: " + linha.getKey() + " Porta: " + linha.getValue());

		}

	}

	public Integer getTabelaPortas(Integer chave) {
		return tabelaPortas.get(chave);
	}

	public int getPorta() {
		return porta;
	}
}
