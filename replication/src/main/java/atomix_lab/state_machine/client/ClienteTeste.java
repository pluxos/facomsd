package atomix_lab.state_machine.client;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.LinkedList;
import java.util.List;

import atomix_lab.state_machine.command.UpdateCommand;
import atomix_lab.state_machine.command.DeleteCommand;
import atomix_lab.state_machine.command.CreateCommand;
import atomix_lab.state_machine.command.ReadQuery;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.StateMachine;

public class ClienteTeste extends StateMachine
{
    public static void main( String[] args ){
    	List<Address> addresses = new LinkedList<>();

        CopycatClient.Builder builder = CopycatClient.builder()
                                                     .withTransport( NettyTransport.builder()
                                                     .withThreads(4)
                                                     .build());
        CopycatClient client = builder.build();

        for(int i = 0; i <args.length;i+=2)
    	{
            Address address = new Address(args[i], Integer.parseInt(args[i+1]));
    		addresses.add(address);
    	}
        
        CompletableFuture<CopycatClient> future = client.connect(addresses);
        future.join();
/*
        CompletableFuture[] futures = new CompletableFuture[]{
           client.submit(new AddVertexCommand(1,1, "vertice1")),
           client.submit(new AddVertexCommand(2,1, "vertice2")),
           client.submit(new AddVertexCommand(3,1, "vertice3")),
           client.submit(new AddVertexCommand(4,2, "vertice4")),
           client.submit(new AddEdgeCommand(1,2, "Edge from 1 to 2")),
           client.submit(new AddEdgeCommand(1,3, "Edge")),
           client.submit(new AddEdgeCommand(1,4, "Edge")),
           client.submit(new AddEdgeCommand(4,3, "Edge"))
        };

        CompletableFuture.allOf(futures).thenRun(() -> System.out.println("Commands completed!"));
        
        try {
        	System.out.println("1: " + client.submit(new GetVertexQuery(1)).get());
        	System.out.println("2: " + client.submit(new GetVertexQuery(2)).get());
        } catch (Exception e)
        {
        	System.out.println("Commands may have failed.");
        	e.printStackTrace();
        }
        
        client.submit(new GetEdgeQuery(1,2)).thenAccept(result -> {
            System.out.println("1-2: " + result);
		});*/
		
		client.submit(new CreateCommand(new BigInteger("100"),"Input1")).thenAccept(result -> System.out.println((result == true)? "Criado com sucesso": "Falha na Criacao" ));
      	client.submit(new CreateCommand(new BigInteger("200"),"Input2")).thenAccept(result -> System.out.println((result == true)? "Criado com sucesso": "Falha na Criacao" ));
     	client.submit(new ReadQuery(new BigInteger("200"))).thenAccept(result -> System.out.println(result));
      	client.submit(new UpdateCommand(new BigInteger("200"), "InputAlter")).thenAccept(result -> System.out.println((result == true)? "Update efetuado": "Falha no Update" ));
      	client.submit(new ReadQuery(new BigInteger("200"))).thenAccept(result -> System.out.println(result));
      	client.submit(new DeleteCommand(new BigInteger("200"))).thenAccept(result -> System.out.println((result == true)? "Deletado com sucesso": "Falha na Deleção" ));
      	client.submit(new ReadQuery(new BigInteger("200"))).thenAccept(result -> System.out.println(result));
      	client.submit(new ReadQuery(new BigInteger("100"))).thenAccept(result -> System.out.println(result));
      	client.submit(new DeleteCommand(new BigInteger("100"))).thenAccept(result -> System.out.println((result == true)? "Deletado com sucesso": "Falha na Deleção" ));
  
		Thread menu = new Thread(new Interface(client));
		menu.start();
		 }
}
