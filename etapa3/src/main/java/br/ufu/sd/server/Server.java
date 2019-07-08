package br.ufu.sd.server;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

import br.ufu.sd.util.OperacaoOutput;
import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.AtomixBuilder;
import io.atomix.core.map.DistributedMap;
import io.atomix.core.profile.ConsensusProfile;
import io.atomix.utils.net.Address;

public class Server {

	private int id;
	private String memberHost;
	private int memberPort;
	private String memberHost1;
	private int memberPort1;
	private String memberHost2;
	private int memberPort2;
	private String memberHost3;
	private int memberPort3;
	
	
	private Atomix atomix;
	
	private int socketPort;
	private ServerSocket serverSocket;
	
	
	private ArrayBlockingQueue<OperacaoOutput> filaComandos;
	private ArrayBlockingQueue<OperacaoOutput> filaBanco;
	private DistributedMap<BigInteger, byte[]> banco;

	public Server(int id, String memberHost, int memberPort, String memberHost1, int memberPort1, String memberHost2,
			int memberPort2, String memberHost3, int memberPort3, int socketPort) {
		
		this.id = id;
		this.memberHost = memberHost;
		this.memberPort = memberPort;
		this.memberHost1 = memberHost1;
		this.memberPort1 = memberPort1;
		this.memberHost2 = memberHost2;
		this.memberPort2 = memberPort2;
		this.memberHost3 = memberHost3;
		this.memberPort3 = memberPort3;
		
		this.socketPort = socketPort;
		
		this.filaComandos = new ArrayBlockingQueue<>(20);
		this.filaBanco = new ArrayBlockingQueue<>(20);
	}

	public void startServer() {

		atomixBuild();
		atomix.start().join();

		// Map distribuido entre todos os membros da cluster
		// Vai ser utilizado para sincrinizar os valores nos bancos
		this.banco = atomix.<BigInteger, byte[]>mapBuilder("banco").build();
		
		try {
			serverSocket = new ServerSocket(socketPort);
		} catch (IOException e) {
			System.out.println("Nao foi possivel iniciar o server na porta informada");
			e.printStackTrace();
			return;
		}
		// Thread responsavel por retirar da fila comandos e inserir nas filas para
		// inserir no banco e no log
		Consumer consumer = new Consumer(filaComandos, filaBanco);
		Thread threadConsumer = new Thread(consumer);
		threadConsumer.setDaemon(true);
		threadConsumer.start();

		InsereBanco insereBanco = new InsereBanco(filaBanco, banco);
		Thread threadInsereBanco = new Thread(insereBanco);
		threadInsereBanco.setDaemon(true);
		threadInsereBanco.start();

		
		ConexaoClient conexaoClient = new ConexaoClient(serverSocket, filaComandos);
		Thread threadConexaoClient = new Thread(conexaoClient);
		threadConexaoClient.setDaemon(true);
		threadConexaoClient.start();
		

		System.out.println("Servidor "+id+" online");
		Scanner sc = new Scanner(System.in);
		String close;
		while (true) {
			close = sc.next();
			if (close.equals("close")) {
				System.out.println("Finalizando servidor");
				break;
			}
			//Util para ajuda nos testes
			if(close.equals("clear")) {
				System.out.println("Limpando banco");
				banco.clear();
			}
		}

		try {
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("Erro "+e.getMessage());
		}
		atomix.stop();
		sc.close();

	}

	// metodo que vai configurar e criar a cluster e inserir o membro
	// vao ser 3 membros, com ip e porta definidas em arquivo
	public void atomixBuild() {

		AtomixBuilder builder = Atomix.builder();
		atomix = builder.withMemberId("member-" + id).withAddress(new Address(memberHost, memberPort))
				.withMembershipProvider(BootstrapDiscoveryProvider.builder()
						.withNodes(
								Node.builder().withId("member-1").withAddress(new Address(memberHost1, memberPort1)).build(),
								Node.builder().withId("member-2").withAddress(new Address(memberHost2, memberPort2)).build(),
								Node.builder().withId("member-3").withAddress(new Address(memberHost3, memberPort3)).build())
						.build())
				.withProfiles(ConsensusProfile.builder().withDataPath("\\tmp\\member-" + id)
						.withMembers("member-1", "member-2", "member-3").build())
				.build();
	}
}
