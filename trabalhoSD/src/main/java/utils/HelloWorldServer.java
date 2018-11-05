package utils;

import java.io.IOException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import servidor.ServerClass;

@Component(immediate = true)
public class HelloWorldServer implements GrpcServer {
  private final int port = 5000;
  private Server server;
  private BindableService greeterService;
  
  @Activate
  public void activate() throws InterruptedException {
    start();
  }
  
  private void start() throws InterruptedException {
    try {
      System.out.println("iniciando");
      Server server = ServerBuilder
          .forPort(9877)
          .addService(new ServerClass()).build();
 
        server.start();
        System.out.println("Server iniciado");
        server.awaitTermination();
    } catch (IOException ex) {
    }
  }
  
  @Reference
  public void setGreeterService(BindableService greeterService) {
    this.greeterService = greeterService;
  }
  
  @Deactivate
  public void deactivate() {
    if (server != null) {
      server.shutdown();
    }
  }
  
  
}