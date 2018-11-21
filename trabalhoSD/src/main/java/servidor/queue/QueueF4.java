package servidor.queue;

import java.math.BigInteger;

import com.servidor.grpc.aplicationGRPC.api.GreeterGrpc;
import com.servidor.grpc.aplicationGRPC.api.Reply;
import com.servidor.grpc.aplicationGRPC.api.Request;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import servidor.ClientData;
import servidor.Finger;
import servidor.command.ExecuteCommand;
import utils.Constant;

public class QueueF4 extends Queue implements Runnable {

	public QueueF4(QueueCommand queue, Finger finger) {
		super(queue, finger);
	}

	ExecuteCommand execute = new ExecuteCommand();
	ManagedChannel channel;

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		try {
			System.out.println("Iniciando F4");

			while (true) {
				ClientData elemento = super.queue.consumeF4();
				StreamObserver<Reply> responseObserver = new StreamObserver<Reply>() {
					@Override
					public void onNext(Reply value) {
						System.out.println(">>Resposta recebida");
						elemento.sendReply(value.getMessage());
					}

					@Override
					public void onError(Throwable t) {
						// TODO Auto-generated method stub
						t.printStackTrace();
					}

					@Override
					public void onCompleted() {
						// TODO Auto-generated method stub
					}
				};

//				if (elemento.getKey().compareTo(super.finger.getMaxKey()) > 0) {
//					// passa direita
//					System.out.println("Key esta a direita");
//					channel = ManagedChannelBuilder.forAddress(super.finger.getAddress(), super.finger.getSucessor())
//							.usePlaintext(true).build();
//				} else if (elemento.getKey().compareTo(super.finger.getMinKey()) < 0) {
//					// passa esquerda
//					System.out.println("Key esta a esquerda");
//					channel = ManagedChannelBuilder.forAddress(super.finger.getAddress(), super.finger.getAntecessor())
//							.usePlaintext(true).build();
//				}
				
				channel = ManagedChannelBuilder.forAddress(super.finger.getAddress(), checkWay(elemento))
						.usePlaintext(true).build();

				GreeterGrpc.GreeterStub asyncStub = GreeterGrpc.newStub(channel);
				Request request = Request.newBuilder().setName(elemento.getComando()).build();
				//asyncStub.send(request, responseObserver);

				String tipo = elemento.getComando().split(" ")[0].toLowerCase();
				if (tipo.equals("create")) {
					asyncStub.create(request, responseObserver);

				} else if (tipo.equals("read")) {
					asyncStub.read(request, responseObserver);
				}

				else if (tipo.equals("update")) {
					asyncStub.update(request, responseObserver);
				}

				else if (tipo.equals("delete")) {
					asyncStub.delete(request, responseObserver);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int checkWay(ClientData elemento) {
		BigInteger antecessor, sucessor, key;
		key = elemento.getKey();
        if (key.compareTo(super.finger.getMaxKey()) == 1) {   // key maior
            antecessor = key.subtract(super.finger.getMaxKey());
            sucessor = super.finger.getMaxKey().add(Constant.maxKey.subtract(key));
        } else {
        	sucessor = Constant.maxKey.subtract(super.finger.getMaxKey().add(key));
        	 antecessor = super.finger.getMaxKey().subtract(key);
        }
        
        if (sucessor.compareTo(antecessor) <= 0) {
        	// server da esquerda
        	return finger.getAntecessor();
        }
        else {
        	// server da direita
        	return finger.getSucessor();
        }
	}
}
