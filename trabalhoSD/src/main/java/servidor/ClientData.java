package servidor;

import com.stackleader.training.grpc.helloworld.api.Reply;

import io.grpc.stub.StreamObserver;
import servidor.dataBase.Data;

public class ClientData {
  private String comando;
  private Data data;
  StreamObserver<Reply> responseObserver;
  public Data getData() {
    return data;
  }
  
  public void setData(Data data) {
    this.data = data;
  }
  
  public  StreamObserver<Reply> getOut() {
    return responseObserver;
  }
  
  public void setOut( StreamObserver<Reply> reply) {
    this.responseObserver = reply;
  }
  
  public String getComando() {
    return comando;
  }
  
  public void setComando(String comando) {
    this.comando = comando;
  }
  
  public void sendReply(String s) {
    Reply response = Reply.newBuilder().setMessage(s).build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
