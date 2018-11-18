package servidor;

import java.io.File;
import java.math.BigInteger;
import java.util.concurrent.Semaphore;

import com.servidor.grpc.aplicationGRPC.api.GreeterGrpc;
import com.servidor.grpc.aplicationGRPC.api.Reply;
import com.servidor.grpc.aplicationGRPC.api.Request;

import io.grpc.BindableService;
import io.grpc.stub.StreamObserver;
import servidor.dataBase.Data;
import servidor.dataBase.RecoveryData;
import servidor.queue.QueueCommand;

public class ServerClass extends GreeterGrpc.GreeterImplBase implements BindableService {
	public static Semaphore mutex_f1 = new Semaphore(1);
	public static Semaphore mutex = new Semaphore(1);
	private QueueCommand queueCommand = null;
	private Queue queue = null;
	private Data dataBase;
	private Finger finger;

	public ServerClass(String andress, int port, BigInteger id, BigInteger minKey, BigInteger maxKey, int antecessor,
			int sucessor) {
		try {
			System.out.println("iniciando serverClass");
			finger = new Finger(andress, port, id, minKey, maxKey, antecessor, sucessor);
			finger.print();
			dataBase = new Data();
			RecoveryData recovery = new RecoveryData();
			File diretorio = new File("logs\\"+finger.getId().toString());
			if(diretorio.exists()) {
				// recupera chaves
				System.out.println("Recuperando chaves");
				
				recovery.recovery(dataBase,finger);
				
			}
			else {
				diretorio.mkdirs();
			}
			// serverSocket = new ServerSocket(Constant.SERVER_PORT);
			queueCommand = new QueueCommand();
			
			
			queue = new Queue(queueCommand, dataBase, finger);
			queue.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ServerClass(String andress, int port, BigInteger id, BigInteger minKey, BigInteger maxKey) {
		try {
			System.out.println("iniciando serverClass");
			finger = new Finger(andress, port, id, minKey, maxKey);
			finger.print();
			dataBase = new Data();
			// serverSocket = new ServerSocket(Constant.SERVER_PORT);
			queueCommand = new QueueCommand();
			RecoveryData recovery = new RecoveryData();
			recovery.recovery(dataBase,finger);
			queue = new Queue(queueCommand, dataBase, finger);
			queue.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void send(Request req, StreamObserver<Reply> responseObserver) {
		HandlerThreadServer h = new HandlerThreadServer(queueCommand, req, responseObserver, finger);
		h.run();
		try {
			h.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void create(Request req, StreamObserver<Reply> responseObserver) {
		HandlerThreadServer h = new HandlerThreadServer(queueCommand, req, responseObserver, finger);
		h.run();
		try {
			h.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Request req, StreamObserver<Reply> responseObserver) {
		HandlerThreadServer h = new HandlerThreadServer(queueCommand, req, responseObserver, finger);
		h.run();
		try {
			h.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Request req, StreamObserver<Reply> responseObserver) {
		HandlerThreadServer h = new HandlerThreadServer(queueCommand, req, responseObserver, finger);
		h.run();
		try {
			h.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void read(Request req, StreamObserver<Reply> responseObserver) {
		HandlerThreadServer h = new HandlerThreadServer(queueCommand, req, responseObserver, finger);
		h.run();
		try {
			h.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}