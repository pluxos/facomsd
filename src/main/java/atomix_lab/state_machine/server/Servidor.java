package atomix_lab.state_machine.server;

import java.io.File;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import atomix_lab.state_machine.server.*;

import atomix_lab.state_machine.command.CreateCommand;
import atomix_lab.state_machine.command.DeleteCommand;
import atomix_lab.state_machine.command.ReadQuery;
import atomix_lab.state_machine.command.UpdateCommand;
import atomix_lab.state_machine.type.Data;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

public class Servidor extends StateMachine {

    private static BlockingQueue < Comando > Fila_F1 = new LinkedBlockingDeque < >();
	  private static BlockingQueue < Comando > Fila_F2 = new LinkedBlockingDeque < >();
	  private static BlockingQueue < Comando > Fila_F3 = new LinkedBlockingDeque < >();
    private Mapa mapa = new Mapa();

    public Boolean Create(Commit<CreateCommand> commit){
        try{
            int k = 10;
            CreateCommand aec = commit.operation( );
            Comando com = new Comando (1, aec.key, aec.value);
            Fila_F1.add(com);

            k = mapa.create(aec.key, aec.value);
            Data e = new Data(aec.key, aec.value);

            if(k == 0){
                System.out.println("Adding ");
                return true;
            }else
              return false;

        }finally{
            commit.close();
        }
    }

    public Data Read(Commit<ReadQuery> commit){
    	try{
            ReadQuery geq = commit.operation();
            Comando com = new Comando (2,geq.key, null);
            Fila_F1.add(com);
            Data result = new Data(geq.key,mapa.read(geq.key));
    		System.out.println("Read = " + result.toString());
            return result;
        }finally{
            commit.close();
        }
    }

    public Boolean Update(Commit<UpdateCommand> commit){
        try{
            UpdateCommand aec = commit.operation( );
            mapa.update(aec.key, aec.value);
            Data e = new Data(aec.key, aec.value);
            System.out.println("Update " + e);
            System.out.println("Novo mapa:");
            mapa.imprimeMapa();
            return mapa.existe(aec.key);
        }finally{
            commit.close();
        }
    }

    public Boolean Delete(Commit<DeleteCommand> commit){
        try{
            DeleteCommand aec = commit.operation( );
            Data e = new Data(aec.key,mapa.read(aec.key));
            mapa.delete(aec.key);
            System.out.println("Deleted " + e);

            return !mapa.existe(aec.key);
        }finally{
            commit.close();
        }
    }

    public static void main( String[] args ){
    	int myId = Integer.parseInt(args[0]);
    	List<Address> addresses = new LinkedList<>();

    	for(int i = 1; i < args.length; i+=2){
        Address address = new Address(args[i], Integer.parseInt(args[i+1]));
    		addresses.add(address);
    	}

        CopycatServer.Builder builder = CopycatServer.builder(addresses.get(myId))
           .withStateMachine(Servidor::new)
           .withTransport( NettyTransport.builder()
                 .withThreads(4)
                 .build())
           .withStorage( Storage.builder()
                 .withDirectory(new File("logs_"+myId)) //Must be unique
                 .withStorageLevel(StorageLevel.DISK)
                 .build());

        CopycatServer server = builder.build();

        Thread t1 = new Thread(new Fila1Manager(Fila_F1, Fila_F2, Fila_F3)); //pega o que estiver na fila f1 e manda para fila f2 e fila f3
        t1.start();
        Thread t2 = new Thread(new MapManager(Fila_F2)); //pega o que estiver na fila f2 e faz as operacoes na memoria
        t2.start();
        Thread t3 = new Thread(new LogFileManager(Fila_F3, Fila_F2)); //pega o que estiver na fila f3 e faz as operacoes do log
        t3.start();
        System.out.println("Servidor inicializado");

        if(myId == 0){
            server.bootstrap().join();
        } else {
            server.join(addresses).join();
        }
    }
}

class Mapa {

	private Map < BigInteger,
	byte[] > mapa;

	public Mapa() {
		this.mapa = new HashMap < >();
	}

	public boolean existe(BigInteger o1) {
		if (mapa.get(o1) == null) return false;
		else return true;
	}

	public int create(BigInteger o1, byte[] o2) {
		if (!existe(o1)) {
			mapa.put(o1, o2);
			return 0;
		} else return - 1;
	}

	public int update(BigInteger o1, byte[] o2) {
		if (existe(o1)) {
			mapa.remove(o1);
			mapa.put(o1, o2);
			return 0;
		} else return 1;
	}

	public int delete(BigInteger o1) {
		if (existe(o1)) {
			mapa.remove(o1);
			return 0;
		} else return 1;
	}

	public byte[] read(BigInteger o1) {
		return mapa.get(o1);
	}

	public Map < BigInteger,
	byte[] > getMapa() {
		return mapa;
    }


    public void imprimeMapa(){
        for (Map.Entry<BigInteger, byte[]> entry : mapa.entrySet()) {
            System.out.println(entry.getKey() + "/" + new String(entry.getValue()));
        }
    }

}

class Fila1Manager implements Runnable {

	private BlockingQueue < Comando > Fila_F1;
	private BlockingQueue < Comando > Fila_F2;
	private BlockingQueue < Comando > Fila_F3;

	public Fila1Manager(BlockingQueue < Comando > f1, BlockingQueue < Comando > f2, BlockingQueue < Comando > f3) {
		Fila_F1 = f1;
		Fila_F2 = f2;
		Fila_F3 = f3;
	}

	// Distribuicao de comandos de F1 para F2 e F3
	public void run() {
		Comando comando;
		System.out.println("Fila1Manager rodando");
		while (true) {
			try {
				comando = (Comando) Fila_F1.take();
				System.out.println(comando);
				Fila_F2.put(comando);
				Fila_F3.put(comando);
			} catch(InterruptedException e) {

			}
		}
	}

}


class MapManager implements Runnable {

	private Mapa mapa;
	private BlockingQueue < Comando > Fila_F2;

	public MapManager(BlockingQueue < Comando > f2) {
		Fila_F2 = f2;
		mapa = new Mapa();
	}

	// Recebe Comandos de  F2 para inserir na memoria
	public void run() {
		Comando comando;
		int opcao;
		int flag = -1;
		System.out.println("MapManager rodando");
		while (true) {
			try {
				comando = (Comando) Fila_F2.take();
                opcao = comando.getOperacao();
                Data dado = new Data(comando.getChave(), comando.getValor());
				// CREATE
				if (opcao == 1) {
					flag = mapa.create(dado.key, dado.value);
				}
				// GET
				else if (opcao == 2) {
					//comando.getCliente().println(new String(mapa.read(comando.getChave())));
					flag = 0;
				}
				// UPDATE
				else if (opcao == 3) {
					flag = mapa.update(dado.key, dado.value);
				}
				// DELETE
				else if (opcao == 4) {
					flag = mapa.delete(dado.key);
				}
				//if (flag == 1) comando.getCliente().println("Falha na operacao!");
			} catch(InterruptedException e) {} catch(NullPointerException a) {}
		}
	}

}
