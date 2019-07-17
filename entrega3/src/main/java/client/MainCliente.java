package client;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

import command.*;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.StateMachine;

public class MainCliente extends StateMachine
{	

	
	public static void main(String[] args) throws Exception 
	{
		List<Address> addresses = new LinkedList<>();
		
		CopycatClient.Builder builder;
		builder = CopycatClient.builder().withTransport(NettyTransport.builder().withThreads(4).build());

		CopycatClient client = builder.build();

		for(int i = 0; i <args.length; i += 2)
    	{
            Address address = new Address(args[i], Integer.parseInt(args[i+1]));
    		addresses.add(address);
    	}

		CompletableFuture<CopycatClient> future = client.connect(addresses);
		future.join();

		Scanner scanner = new Scanner( System.in );

		try
		{
			int op, key;
			String value;
			
			do
			{
				System.out.println("Opcoes disponiveis:");
		
				System.out.println("\n1 - Create");
				System.out.println("2 - Update");
				System.out.println("3 - Read");
				System.out.println("4 - Delete");
				System.out.println("5 - Sair\n");

				System.out.print("\nOpcão: ");
				op = scanner.nextInt();

				switch(op)
				{
					case 1:
						System.out.print("Chave: ");
						key = scanner.nextInt();
						System.out.print("\nValor: ");
						value = scanner.nextLine();
						client.submit(new CreateCommand(key, value));
						break;

					case 2:
						System.out.print("Chave: ");
						key = scanner.nextInt();
						System.out.print("\nNovo Valor: ");
						value = scanner.nextLine();
						break;
					
					case 3:
						System.out.print("Chave: ");
						key = scanner.nextInt();
						break;

					case 4:
						System.out.print("Chave: ");
						key = scanner.nextInt();
						break;

					case 5:
						System.out.println("Saindo...");
						break;

					default:
						System.out.println("Opcao Invalida");
						break;
				}

			}
			while(op > 0 && op < 5);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
}
