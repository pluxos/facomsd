package servidor;

import com.stackleader.training.grpc.helloworld.api.HelloReply;

import io.grpc.stub.StreamObserver;
import servidor.dataBase.Data;

public class ClientData {
  private String comando;
  private Data data;
  StreamObserver<HelloReply> responseObserver;
  public Data getData() {
    return data;
  }
  
  public void setData(Data data) {
    this.data = data;
  }
  
  public  StreamObserver<HelloReply> getOut() {
    return responseObserver;
  }
  
  public void setOut( StreamObserver<HelloReply> reply) {
    this.responseObserver = reply;
  }
  
  public String getComando() {
    return comando;
  }
  
  public void setComando(String comando) {
    this.comando = comando;
  }
  
  public void sendReply(String s) {
    HelloReply response = HelloReply.newBuilder().setMessage(s).build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
