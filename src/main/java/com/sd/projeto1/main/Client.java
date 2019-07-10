package com.sd.projeto1.main;

import com.sd.projeto1.command.AddCommand;
import com.sd.projeto1.command.GetCommand;
import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.model.MapaDTO;
import com.sd.projeto1.util.PropertyManagement;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.StateMachine;
import org.apache.commons.lang3.SerializationUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends StateMachine {

    private static Queue<DatagramPacket> comandos = new LinkedList<>();
    private static DatagramSocket socketCliente;
    private static InetAddress enderecoIP;
   // private static CopycatClient client;

    static PropertyManagement pm = new PropertyManagement();

    public static void main(String[] args) {
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
        Thread receive = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        DatagramPacket pacoteRecebido = new DatagramPacket(receiveData, receiveData.length);
                        socketCliente.receive(pacoteRecebido);
                        //String msg = new String(pacoteRecebido.getData(), 0, pacoteRecebido.getLength());
                        MapaDTO maparetorno = (MapaDTO) SerializationUtils.deserialize(pacoteRecebido.getData());

                        if (maparetorno == null) {
                            System.out.println(maparetorno.getMensagem());
                        } else {
                            if (maparetorno.getMapa().getTipoOperacaoId() == 4) {
                                objetoRetornado(maparetorno);
                            } else {
                                System.out.println(maparetorno.getMensagem());
                            }
                        }

                    }
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
*/
        Thread send = new Thread(() -> {
            try {
                while (true) {
                    menu(client);
                    Thread.sleep(2000);
                }

            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        send.start();
        try {
            send.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // executor.execute(receive);
        //executor.execute(send);

        //executor.shutdown();
    }

    public static DatagramPacket send(byte[] outData) throws IOException {

        DatagramPacket sendPacket = new DatagramPacket(outData, outData.length, enderecoIP, pm.getPort());
        socketCliente.send(sendPacket);

        return sendPacket;
    }

    public static DatagramPacket receive(byte[] inData) throws IOException {

        DatagramPacket in = new DatagramPacket(inData, inData.length);
        socketCliente.receive(in);

        return in;
    }

    public static void menu(CopycatClient client) throws Exception {

        int opcao = 0, chave;
        String msg;
        BufferedReader mensagem;
        Mapa mapa;
        mensagem = new BufferedReader(new InputStreamReader(System.in));
        Long key = 0l;

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===============================");
        System.out.println("Digite a operação: ");
        System.out.println("1 - Inserir");
        System.out.println("2 - Atualizar");
        System.out.println("3 - Excluir");
        System.out.println("4 - Buscar");
        System.out.println("5 - Sair");
        System.out.println("Opção:");

        opcao = scanner.nextInt();


        switch (opcao) {
            case 1:

                System.out.println("Digite a chave:");
                key = scanner.nextLong();

                System.out.println("Digite a mensagem:");
                msg = mensagem.readLine();

                mapa = new Mapa();

                mapa.setTipoOperacaoId(1);
                mapa.setTexto(msg);

                byte[] object = SerializationUtils.serialize(mapa);

                if (object.length > 1400) {
                    System.out.println("Pacote maior que o suportado!");
                } else {
                    //send(object);
                    CompletableFuture<Boolean> future = client.submit(new AddCommand(Long.valueOf(key), msg));
                    Object result = future.get();
                    System.out.println(String.valueOf(result));
                }

                break;
            case 2:
                System.out.println("Digite a chave da mensagem que deseja atualizar:");
                key = scanner.nextLong();

                System.out.println("Digite a Mensagem:");
                msg = mensagem.readLine();



                if (msg.length() > 1400) {
                    System.out.println("Pacote maior que o suportado!");
                } else {
                    CompletableFuture<Object> future = client.submit(new GetCommand(key));
                    Object result = future.get();
                    System.out.println(String.valueOf(result));
                }

                break;
            case 3:
                System.out.println("Digite a chave da mensagem que deseja excluir:");
                key = scanner.nextLong();


                CompletableFuture<Object> future = client.submit(new GetCommand(key));
                Object result = future.get();
                System.out.println(String.valueOf(result));

                break;
            case 4:
                System.out.println("Digite a chave da mensagem que deseja buscar:");
                key = scanner.nextLong();


                CompletableFuture<Object> futureGet = client.submit(new GetCommand(key));
                System.out.println(String.valueOf(futureGet.get()));

                break;
            case 5:
                System.exit(1);
            default:
                System.out.println("Opção Inválida");
                break;
        }
    }



    public static void objetoRetornado(MapaDTO mapa) {
        System.out.println("\n================================");
        System.out.println("Chave: " + mapa.getMapa().getChave());
        System.out.println("Texto: " + mapa.getMapa().getTexto());
    }


}
