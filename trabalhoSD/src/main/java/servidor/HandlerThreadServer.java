package servidor;

import com.servidor.grpc.aplicationGRPC.api.Reply;
import com.servidor.grpc.aplicationGRPC.api.Request;

import io.grpc.stub.StreamObserver;
import servidor.queue.QueueCommand;

public class HandlerThreadServer extends Thread {
	private String comando;
	private QueueCommand queueCommand;
	private ClientData clientComand;
//  private Data data;
	private Request req;
	private Finger finger;
	StreamObserver<Reply> responseObserver;

	public HandlerThreadServer(QueueCommand queueCommand, Request req, StreamObserver<Reply> responseObserver,
			Finger finger) {
		try {
			this.finger = finger;
			this.responseObserver = responseObserver;
			this.queueCommand = queueCommand;
//      this.data = data;
			this.req = req;
//      System.out.println(">>>>> Cliente conectado");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			clientComand = new ClientData();
			comando = this.req.getName();
			System.out.println(">>> Recebido: " + comando);
			if (HandlerCommandClient.checkComand(comando)) {
//        clientComand.setData(data);
				clientComand.setComando(comando);
				clientComand.setOut(responseObserver);
				System.out.println("                    Key: " + clientComand.getKey());
				if (clientComand.getKey().compareTo(finger.getMinKey()) >= 0 && clientComand.getKey().compareTo(finger.getMaxKey()) <= 0) {
					queueCommand.produceF1(clientComand);
				} else if (finger.getPort() == finger.getAntecessor() || finger.getPort() == finger.getSucessor()) {// é unico no do anel
					clientComand.setOut(responseObserver);
					clientComand.sendReply("Erro, verifique a key informada");
				} else {
					System.out.println("adicionando em f4");
					queueCommand.produceF4(clientComand);
				}
			} else {
				System.out.println("Syntaxe ou comando incorreto!");
				clientComand.setOut(responseObserver);
				clientComand.sendReply("Syntaxe ou comando incorreto!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
