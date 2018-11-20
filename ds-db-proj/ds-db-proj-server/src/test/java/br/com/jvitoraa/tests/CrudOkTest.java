package br.com.jvitoraa.tests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.jvitoraa.client.GrpcClient;
import br.com.jvitoraa.server.GrpcServer;
import lombok.Data;

@Data
@RunWith(MockitoJUnitRunner.class)
public class CrudOkTest {
	
	private GrpcClient client;
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
	
	@After
	public void killAll() {
		tServer1.interrupt();
		tServer2.interrupt();
		tServer3.interrupt();
		tServer4.interrupt();
		tServer5.interrupt();
	}
	
	@Test
	public void crudeOkTest () throws InterruptedException {
		
		ByteArrayInputStream in = new ByteArrayInputStream("create 254 joao".getBytes());
		System.setIn(in);
		
		this.client = new GrpcClient(8001);
		Thread tClient = this.generateThread(client);
		tClient.start();
		
		Thread.sleep(5000);
		
		assertEquals("joao", this.server1.getDatabaseRepository().getDatabase().get(BigInteger.valueOf(254)));
		tClient.interrupt();
		
		in = new ByteArrayInputStream("read 254".getBytes());
		System.setIn(in);
		
		this.client = new GrpcClient(8001);
		Thread tClientReader = this.generateThread(client);
		tClientReader.start();
		
		Thread.sleep(5000);
		
		assertEquals("joao", this.server1.getDatabaseRepository().read(BigInteger.valueOf(254)));
		tClientReader.interrupt();
		
		in = new ByteArrayInputStream("update 254 aaa".getBytes());
		System.setIn(in);
		
		this.client = new GrpcClient(8001);
		Thread tClientUpdater = this.generateThread(client);
		tClientUpdater.start();
		
		Thread.sleep(5000);
		
		assertEquals("aaa", this.server1.getDatabaseRepository().getDatabase().get(BigInteger.valueOf(254)));
		tClientUpdater.interrupt();
		
		in = new ByteArrayInputStream("delete 254".getBytes());
		System.setIn(in);
		
		this.client = new GrpcClient(8001);
		Thread tCientDelete = this.generateThread(client);
		tCientDelete.start();
		
		Thread.sleep(5000);
		
		assertEquals(null, this.server1.getDatabaseRepository().getDatabase().get(BigInteger.valueOf(254)));
		tCientDelete.interrupt();
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
