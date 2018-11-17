package servidor;

import java.io.IOException;
import java.math.BigInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import io.grpc.Server;
import io.grpc.ServerBuilder;

@Component(immediate = true)
public class Node {
//  private final int port = 5000;
	private Server server;
//  private BindableService greeterService;

//	@Activate
//	public void activate(String andress, int port, BigInteger id, BigInteger minKey, BigInteger maxKey, int antecessor,
//			int sucessor) throws InterruptedException {
//		start(andress, port, id, minKey, maxKey, antecessor, sucessor);
//	}

	public void start(String andress, int port, BigInteger id, BigInteger minKey, BigInteger maxKey, int antecessor,
			int sucessor) throws InterruptedException {
		try {
			System.out.println("iniciando");
			Server server = ServerBuilder.forPort(port)
					.addService(new ServerClass(andress, port, id, minKey, maxKey, antecessor, sucessor)).build();
			server.start();
			System.out.println("Server iniciado");
			server.awaitTermination();
		} catch (IOException ex) {
		}
	}

//  @Reference
//  public void setGreeterService(BindableService greeterService) {
//    this.greeterService = greeterService;
//  }

	@Deactivate
	public void deactivate() {
		if (server != null) {
			server.shutdown();
		}
	}
}