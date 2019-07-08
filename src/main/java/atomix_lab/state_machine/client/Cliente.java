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

public class Cliente extends StateMachine {

    public static void main( String[] args ){
    	List<Address> addresses = new LinkedList<>();

        CopycatClient.Builder builder = CopycatClient.builder()
           .withTransport( NettyTransport.builder()
           .withThreads(4)
           .build());

        CopycatClient client = builder.build();

        // tratar cliente de teste
        int iterServ = 0;
        int testCli = 0;
        if(args.length == 7){
          iterServ = 6;
          testCli = 1;
        }else{
          iterServ = args.length;
        }

        for(int i = 0; i < iterServ; i += 2){
          Address address = new Address(args[i], Integer.parseInt(args[i+1]));
          addresses.add(address);
        }

        CompletableFuture<CopycatClient> future = client.connect(addresses);
        future.join();

    if(testCli != 1){
      // Execução normal
      Thread menu = new Thread(new Interface(client));
  		menu.start();
    }else{
      // Cliente de Teste : mvn test
      client.submit(new CreateCommand(new BigInteger("100"),"Input1")).thenRun(() -> System.out.println("Criado"));;
      client.submit(new CreateCommand(new BigInteger("200"),"Input2")).thenRun(() -> System.out.println("Criado"));;
      client.submit(new ReadQuery(new BigInteger("200"))).thenAccept(result -> System.out.println(result));
      client.submit(new UpdateCommand(new BigInteger("200"), "InputAlter")).thenRun(() -> System.out.println("Update completo"));;
      client.submit(new ReadQuery(new BigInteger("200"))).thenAccept(result -> System.out.println(result));
      client.submit(new DeleteCommand(new BigInteger("200"))).thenRun(() -> System.out.println("Deleted"));
      client.submit(new ReadQuery(new BigInteger("200"))).thenAccept(result -> System.out.println(result));
      client.submit(new ReadQuery(new BigInteger("100"))).thenAccept(result -> System.out.println(result));
      client.submit(new DeleteCommand(new BigInteger("100"))).thenRun(() -> System.out.println("Deleted"));
    }

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
                            client.submit(new CreateCommand(chave,valor)).thenRun(() -> System.out.println("Criado"));;
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
							client.submit(new UpdateCommand(chave, valor)).thenRun(() -> System.out.println("Update completo"));;
						}
						else if (a == 4) {
							System.out.println("Entre com a chave:");
							chave = ler.nextBigInteger();
							client.submit(new DeleteCommand(chave)).thenRun(() -> System.out.println("Deleted"));
						}
						else if (a == 5) {
							System.out.println("Tchau");
							System.exit(0);
						}
					} catch(Exception falha) {
						System.out.println("Erro - " + falha + ".\n Verifique os valores inseridos e tente de novo.");
					}
        } else System.out.println("Erro: Opção inválida");
	}

}
