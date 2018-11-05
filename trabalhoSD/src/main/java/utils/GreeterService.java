package utils;

import com.stackleader.training.grpc.helloworld.api.GreeterGrpc;
import com.stackleader.training.grpc.helloworld.api.HelloReply;
import com.stackleader.training.grpc.helloworld.api.HelloRequest;
import io.grpc.BindableService;
import io.grpc.stub.StreamObserver;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true)
public class GreeterService extends GreeterGrpc.GreeterImplBase implements BindableService{


  @Override
  public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
    System.out.println("mandando resposta");
    HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }

  public void sayHelloAgain(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
    HelloReply reply = HelloReply.newBuilder().setMessage("Hello again " + req.getName()).build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }

}