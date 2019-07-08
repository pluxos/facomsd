package atomix_lab.state_machine.client;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import atomix_lab.state_machine.command.UpdateCommand;
import atomix_lab.state_machine.command.DeleteCommand;
import atomix_lab.state_machine.command.CreateCommand;
import atomix_lab.state_machine.command.ReadQuery;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.StateMachine;

public class Cliente extends StateMachine
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

		Thread menu = new Thread(new Interface(client));
		menu.start();
    }
}

class Interface implements Runnable {

	CopycatClient client;
	private String menu = "Menu:\n" + "1 - Create\n" + "2 - Read\n" + "3 - Update\n" + "4 - Delete\n" + "5 - Quit\n";
	private int resposta;

	public Interface(CopycatClient cliente) {
		client = cliente;
	}

	public void main() {
		Thread op = new Thread(this);
		op.start();
		try {
			op.join();
		} catch(InterruptedException a) {}
	}

	public void run() {
		Scanner leitor = new Scanner(System.in );
		while (! (Thread.currentThread().isInterrupted())) {
			System.out.println(menu);
			resposta = leitor.nextInt();
			validaResposta(resposta);
        }
        leitor.close();
	}

	private void validaResposta(int a) {
		BigInteger chave;
		String valor;
		Scanner ler = new Scanner(System. in );
		if (a > 0 && a < 6) {
				try {
						if (a == 1) {
							System.out.println("Entre com a chave:");
							chave = ler.nextBigInteger();
							ler.nextLine();
							System.out.println("Entre com o valor:");
                            valor = ler.nextLine();
                            client.submit(new CreateCommand(chave,valor)).thenAccept(result -> System.out.println((result == true)? "Criado com sucesso": "Falha na Criacao" ));
						}
						else if (a == 2) {
							System.out.println("Entre com a chave:");
							chave = ler.nextBigInteger();
							client.submit(new ReadQuery(chave)).thenAccept(result -> System.out.println(result));
						}
						else if (a == 3) {
							System.out.println("Entre com a chave:");
							chave = ler.nextBigInteger();
							ler.nextLine();
							System.out.println("Entre com o valor:");
							valor = ler.nextLine();
							client.submit(new UpdateCommand(chave, valor)).thenAccept(result -> System.out.println((result == true)? "Update efetuado": "Falha no Update" ));
						}
						else if (a == 4) {
							System.out.println("Entre com a chave:");
							chave = ler.nextBigInteger();
							client.submit(new DeleteCommand(chave)).thenAccept(result -> System.out.println((result == true)? "Deletado com sucesso": "Falha na Deleção" ));
						}
						else if (a == 5) {
							System.out.println("Tchau");
							System.exit(0);
						}
					} catch(Exception falha) {
						System.out.println("Erro - " + falha + ".\n Verifique os valores inseridos e tente de novo.");
					}
        } else System.out.println("Erro: Opção inválida");
        //ler.close();
	}

}