package br.com.jvitoraa.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.jvitoraa.client.GrpcClient;
import br.com.jvitoraa.server.GrpcServer;
import lombok.Data;

@Data
@RunWith(MockitoJUnitRunner.class)
public class CrudNOkTest {

	private GrpcClient client1;

	private GrpcServer server1;
	private GrpcServer server2;
	private GrpcServer server3;
	private GrpcServer server4;
	private GrpcServer server5;

	private Thread tServer1 = this.generateThread(server1);
	private Thread tServer2 = this.generateThread(server2);
	private Thread tServer3 = this.generateThread(server3);
	private Thread tServer4 = this.generateThread(server4);
	private Thread tServer5 = this.generateThread(server5);

	
	Thread tClient1;
	
	@Before
	public void setUp() throws InterruptedException, IOException {

		this.server1 = new GrpcServer("0");
		this.server2 = new GrpcServer("1");
		this.server3 = new GrpcServer("2");
		this.server4 = new GrpcServer("3");
		this.server5 = new GrpcServer("4");

		tServer1 = this.generateThread(server1);
		tServer2 = this.generateThread(server2);
		tServer3 = this.generateThread(server3);
		tServer4 = this.generateThread(server4);
		tServer5 = this.generateThread(server5);

		tServer1.start();
		tServer2.start();
		tServer3.start();
		tServer4.start();
		tServer5.start();
		
	}

	@Test
	public void crudNokTest() throws InterruptedException {

		this.client1 = new GrpcClient(8001);
		
		tClient1 = this.generateThread(client1);
		
		tClient1.start();
		
		Thread.sleep(10000);
		client1.getClientFacade().create(213L, "JOAO", client1.getObserver());
		Thread.sleep(8000);
		assertEquals("Register created!", client1.getObserver().getResponseText());
		
		client1.getClientFacade().create(213L, "JOAOV", client1.getObserver());
		Thread.sleep(5000);
		assertEquals("Cannot create, Id alredy exists!", client1.getObserver().getResponseText());
		client1.getClientFacade().delete(213L, client1.getObserver());
		
		Thread.sleep(5000);
		client1.getClientFacade().read(213L, client1.getObserver());
		Thread.sleep(5000);
		assertEquals("Register not found!", client1.getObserver().getResponseText());
		client1.getClientFacade().update(213L, "AAA", client1.getObserver());
		Thread.sleep(5000);
		assertEquals("Cannot update, Register not found!", client1.getObserver().getResponseText());
		client1.getClientFacade().delete(213L, client1.getObserver());
		Thread.sleep(5000);
		assertEquals("Cannot delete, Regiser not found!", client1.getObserver().getResponseText());
		
		
	}

	public Thread generateThread(GrpcServer server) {
		return new Thread(() -> {
			try {
				server.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public Thread generateThread(GrpcClient client) {
		return new Thread(() -> {
			try {
				client.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
