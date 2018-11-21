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
public class MultipleClientsTest {

	private GrpcClient client1;
	private GrpcClient client2;
	private GrpcClient client3;
	private GrpcClient client4;
	private GrpcClient client5;
	private GrpcClient client6;
	private GrpcClient client7;
	private GrpcClient client8;
	private GrpcClient client9;
	private GrpcClient client10;

	
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
	Thread tClient2;
	Thread tClient3;
	Thread tClient4;
	Thread tClient5;
	Thread tClient6;
	Thread tClient7;
	Thread tClient8;
	Thread tClient9;
	Thread tClient10;
	
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
	public void multipleClientsTest() throws InterruptedException {

		this.client1 = new GrpcClient(8001);
		this.client2 = new GrpcClient(8001);
		this.client3 = new GrpcClient(8001);
		this.client4 = new GrpcClient(8001);
		this.client5 = new GrpcClient(8001);
		this.client6 = new GrpcClient(8001);
		this.client7 = new GrpcClient(8001);
		this.client8 = new GrpcClient(8001);
		this.client9 = new GrpcClient(8001);
		this.client10= new GrpcClient(8001);
		
		tClient1 = this.generateThread(client1);
		tClient2 = this.generateThread(client2);
		tClient3 = this.generateThread(client3);
		tClient4 = this.generateThread(client4);
		tClient5 = this.generateThread(client5);
		tClient6 = this.generateThread(client6);
		tClient7 = this.generateThread(client7);
		tClient8 = this.generateThread(client8);
		tClient9 = this.generateThread(client9);
		tClient10 = this.generateThread(client10);
		
		tClient1.start();
		tClient2.start();
		tClient3.start();
		tClient4.start();
		tClient5.start();
		tClient6.start();
		tClient7.start();
		tClient8.start(); 
		tClient9.start();
		tClient10.start();
		
		Thread.sleep(10000);
		
		client1.getClientFacade().create(251L, "JOAO", client1.getObserver());
		client2.getClientFacade().create(252L, "AAAA", client2.getObserver());
		client3.getClientFacade().create(253L, "AAAA", client3.getObserver());
		client4.getClientFacade().create(254L, "AAAA", client4.getObserver());
		client5.getClientFacade().create(255L, "AAAA", client5.getObserver());
		client6.getClientFacade().create(241L, "AAAA", client6.getObserver());
		client7.getClientFacade().create(242L, "AAAA", client7.getObserver());
		client8.getClientFacade().create(244L, "AAAA", client8.getObserver());
		client9.getClientFacade().create(245L, "AAAA", client9.getObserver());
		client10.getClientFacade().create(246L, "AAAA", client10.getObserver());
		

		Thread.sleep(8000);
		
		assertEquals("Register created!", client1.getObserver().getResponseText());
		assertEquals("Register created!", client2.getObserver().getResponseText());
		assertEquals("Register created!", client3.getObserver().getResponseText());
		assertEquals("Register created!", client4.getObserver().getResponseText());
		assertEquals("Register created!", client5.getObserver().getResponseText());
		assertEquals("Register created!", client6.getObserver().getResponseText());
		assertEquals("Register created!", client7.getObserver().getResponseText());
		assertEquals("Register created!", client8.getObserver().getResponseText());
		assertEquals("Register created!", client9.getObserver().getResponseText());
		assertEquals("Register created!", client10.getObserver().getResponseText());
		
		
		client1.getClientFacade().delete(251L, client1.getObserver());
		client2.getClientFacade().delete(252L, client2.getObserver());
		client3.getClientFacade().delete(253L, client3.getObserver());
		client4.getClientFacade().delete(254L, client4.getObserver());
		client5.getClientFacade().delete(255L, client5.getObserver());
		client6.getClientFacade().delete(241L, client6.getObserver());
		client7.getClientFacade().delete(242L, client7.getObserver());
		client8.getClientFacade().delete(244L, client8.getObserver());
		client9.getClientFacade().delete(245L, client9.getObserver());
		client10.getClientFacade().delete(246L, client10.getObserver());
		
		Thread.sleep(8000);
		
		assertEquals("Register deleted!", client1.getObserver().getResponseText());
		assertEquals("Register deleted!", client2.getObserver().getResponseText());
		assertEquals("Register deleted!", client3.getObserver().getResponseText());
		assertEquals("Register deleted!", client4.getObserver().getResponseText());
		assertEquals("Register deleted!", client5.getObserver().getResponseText());
		assertEquals("Register deleted!", client6.getObserver().getResponseText());
		assertEquals("Register deleted!", client7.getObserver().getResponseText());
		assertEquals("Register deleted!", client8.getObserver().getResponseText());
		assertEquals("Register deleted!", client9.getObserver().getResponseText());
		assertEquals("Register deleted!", client10.getObserver().getResponseText());
		
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
