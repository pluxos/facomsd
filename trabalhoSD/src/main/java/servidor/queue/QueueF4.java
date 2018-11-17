package servidor.queue;

import com.servidor.grpc.aplicationGRPC.api.GreeterGrpc;
import com.servidor.grpc.aplicationGRPC.api.Reply;
import com.servidor.grpc.aplicationGRPC.api.Request;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import servidor.ClientData;
import servidor.Finger;
import servidor.Queue;
import servidor.command.ExecuteCommand;

public class QueueF4 extends Queue implements Runnable {

	public QueueF4(QueueCommand queue, Finger finger) {
		super(queue, finger);
	}

	ExecuteCommand execute = new ExecuteCommand();
	ManagedChannel channel;

	@Override
	public void run() {
		try {
			System.out.println("Iniciando F4");
			
			while (true) {
				ClientData elemento = super.queue.consumeF4();
				
				if(elemento.getKey().compareTo(super.finger.getMaxKey()) > 0) {
					//passa direita
					System.out.println("esta a direita");
					channel = ManagedChannelBuilder.forAddress(super.finger.getAddress(), super.finger.getSucessor()).usePlaintext(true)
							.build();
				}
				else if(elemento.getKey().compareTo(super.finger.getMinKey()) < 0) {
					// passa esquerda
					System.out.println("esta a esquerda");
					channel = ManagedChannelBuilder.forAddress(super.finger.getAddress(), super.finger.getAntecessor()).usePlaintext(true)
							.build();
				}
				
				
				StreamObserver<Reply> responseObserver = new StreamObserver<Reply>() {
					@Override
					public void onNext(Reply value) {
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
				
				GreeterGrpc.GreeterStub asyncStub = GreeterGrpc.newStub(channel);
				Request request = Request.newBuilder().setName(elemento.getComando()).build();
				asyncStub.send(request, responseObserver);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
