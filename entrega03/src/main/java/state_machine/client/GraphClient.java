package state_machine.client;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.LinkedList;
import java.util.List;

import state_machine.command.*;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.StateMachine;

public class GraphClient extends StateMachine {
    public static void main(String[] args) {
        List<Address> addresses = new LinkedList<>();

        CopycatClient.Builder builder = CopycatClient.builder()
                .withTransport(NettyTransport.builder()
                        .withThreads(4)
                        .build());
        CopycatClient client = builder.build();

        for (int i = 0; i < args.length; i += 2) {
            Address address = new Address(args[i], Integer.parseInt(args[i + 1]));
            addresses.add(address);
        }

        CompletableFuture<CopycatClient> future = client.connect(addresses);
        future.join();

        String welcome = "Esse software se baseia em um banco de dados chave valor.\n Chave: Inteiro(Infinito) \n Valor: Bytes\n";
        String options = "Opções disponivéis: \n- Create chave valor \n- Read chave \n- Update chave valor \n- Delete chave \n- Help \n- Sair \n";
        String read = "Digite uma opção válida: ";
        String invalid = "Opção inválida!!!";
        String quit = "Conexão encerrada!!!";
        String close = "Saindo....";
        String option;
        String command;
        String key;
        String value;

        int size;
        int spaceFirst;
        int spaceSecond;

        BigInteger keyBigInteger;
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println(welcome);
            System.out.println(options);
            while (true) {
                System.out.print(read);
                option = scanner.nextLine();
                option = option.toUpperCase();
                if (option.equals("SAIR")) {
                    System.out.println(close);
                    break;
                }
                if (option.equals("HELP")) {
                    System.out.println(options);
                    continue;
                }
                size = option.length();
                spaceFirst = option.indexOf(" ");

                if( spaceFirst == -1 ) {
                    throw new Exception();
                }
                command = option.substring(0, spaceFirst);
                if (command.equals("CREATE")) {
                    spaceSecond = option.indexOf(" ", (spaceFirst + 1));
                    key = option.substring( ( spaceFirst + 1 ), spaceSecond );
                    keyBigInteger = new BigInteger( key );
                    value = option.substring( ( spaceSecond + 1 ), size );
                    System.out.println(client.submit(new CreateItemCommand(keyBigInteger, value)).get());
                }
                if (command.equals("UPDATE")) {
                    spaceSecond = option.indexOf(" ", (spaceFirst + 1));
                    key = option.substring( ( spaceFirst + 1 ), spaceSecond );
                    keyBigInteger = new BigInteger( key );
                    value = option.substring( ( spaceSecond + 1 ), size );
                    System.out.println(client.submit(new UpdateItemCommand(keyBigInteger, value)).get());
                }
                if (command.equals("READ")) {
                    key = option.substring( ( spaceFirst + 1 ), size);
                    keyBigInteger = new BigInteger( key );
                    System.out.println(client.submit(new ReadItemQuery(keyBigInteger)).get());
                }
                if(command.equals("DELETE")) {
                    key = option.substring( ( spaceFirst + 1 ), size);
                    keyBigInteger = new BigInteger( key );
                    System.out.println(client.submit(new DeleteItemCommand(keyBigInteger)).get());
                }
            }
            System.out.println(quit);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
//        try {
//            System.out.println("1: " + client.submit(new CreateItemCommand(new BigInteger("123"), "pororo")).get());
//            System.out.println("2: " + client.submit(new ReadItemQuery(new BigInteger("123"))).get());
//            System.out.println("3: " + client.submit(new UpdateItemCommand(new BigInteger("123"), "po")).get());
//            System.out.println("4: " + client.submit(new DeleteItemCommand(new BigInteger("123"))).get());
//        } catch (Exception e) {
//            System.out.println("Commands may have failed.");
//            e.printStackTrace();
//        }
    }
}
