package utils;

import com.servidor.grpc.aplicationGRPC.api.GreeterGrpc;
import com.servidor.grpc.aplicationGRPC.api.Reply;
import com.servidor.grpc.aplicationGRPC.api.Request;
import io.grpc.BindableService;
import io.grpc.stub.StreamObserver;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true)
public class GreeterService extends GreeterGrpc.GreeterImplBase implements BindableService {
	@Override
	public void send(Request req, StreamObserver<Reply> responseObserver) {
		System.out.println("mandando resposta");
		Reply reply = Reply.newBuilder().setMessage("Hello " + req.getName()).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}
}