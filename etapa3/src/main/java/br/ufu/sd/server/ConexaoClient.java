package br.ufu.sd.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.ufu.sd.util.OperacaoOutput;

public class ConexaoClient implements Runnable {

	private ServerSocket serverSocket;
	private ArrayBlockingQueue<OperacaoOutput> filaComandos;
	private ExecutorService threadPool;

	public ConexaoClient(ServerSocket serverSocket,
			ArrayBlockingQueue<OperacaoOutput> filaComandos) {
		this.serverSocket = serverSocket;
		this.filaComandos = filaComandos;
		threadPool = Executors.newCachedThreadPool();

	}

	@Override
	public void run() {

		while (true) {
			try {
				Socket socket = serverSocket.accept();
				System.out.println("Novo Cliente conectado na porta " +socket.getPort());

				//Thread para atender cada cliente
				//Thread pool apenas aqui porque o numero de clientes nao e fixo
				TarefasServer distribuirTarefas = new TarefasServer(socket, filaComandos);
				threadPool.execute(distribuirTarefas);
				
			} catch (IOException e) {
				if (serverSocket.isClosed()) {
					System.out.println("Finalizando Conexao Client");
					break;
				}
			}

		}

	}
}