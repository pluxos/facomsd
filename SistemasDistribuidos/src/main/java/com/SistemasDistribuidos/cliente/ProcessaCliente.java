package com.SistemasDistribuidos.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;


import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.Client;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.StateMachine;
import io.atomix.catalyst.transport.netty.NettyTransport;

import org.apache.commons.lang3.SerializationUtils;

import com.SistemasDistribuidos.models.MapData;
import com.SistemasDistribuidos.utils.GetCommand;
import com.SistemasDistribuidos.utils.ManagementProperties;
import com.SistemasDistribuidos.utils.PostCommand;




/**
 * 
 * @author luizedp
 *
 */
public class ProcessaCliente extends StateMachine {
	
	static Logger log = Logger.getLogger(ProcessaCliente.class.getName());
	DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
	DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
	private static DatagramSocket socketCliente;
	private static InetAddress IPAdress;
	
	static ManagementProperties mp = new ManagementProperties();
	
//	static Thread thread = new Thread();
	
	public static void main(String[] args) {
		
		args = new String[6];		
		args[0]	= "127.0.0.1";     
		args[1] = "8080";
		args[2] = "127.0.0.1";
		args[3] = "8081";
		args[4] = "127.0.0.1";
		args[5] = "8082";

		List<Address> addresses = new LinkedList<>();

		CopycatClient.Builder builder = CopycatClient.builder().withTransport(NettyTransport.builder().withThreads(3).build());
		CopycatClient client = builder.build();

		for (int i = 0; i < args.length; i += 2) {
			Address address = new Address(args[i], Integer.parseInt(args[i + 1]));
			addresses.add(address);
		}

		CompletableFuture<CopycatClient> future = client.connect(addresses);
		future.join();

		Thread thread = new Thread(() ->{
			
//			public void run() {
				try {
					while (true) {
						menu(client);
						Thread.sleep(3000);
					}

				} catch (IOException e) {
					log.log(Level.SEVERE, e.getMessage(), e.getMessage());
				} catch (Exception e) {
					log.log(Level.SEVERE, e.getMessage(), e.getMessage());
				}
//			}
		});
		thread.start();
		try {
			log.info("CHEGUEI AQUI: ");
			thread.join();
		} catch (InterruptedException e) {
			log.info("InterruptedException: " + e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param packet
	 * @return
	 * @throws IOException
	 */
    public static DatagramPacket send(byte[] bytepacket) throws IOException {

        DatagramPacket packet = new DatagramPacket(bytepacket, bytepacket.length, IPAdress, mp.getPort());
        socketCliente.send(packet);

        return packet;
    }
    
    /**
     * 
     * @param packet
     * @return
     * @throws IOException
     */
    public static DatagramPacket receive(byte[] bytepacket) throws IOException {

        DatagramPacket packet = new DatagramPacket(bytepacket, bytepacket.length);
        socketCliente.receive(packet);

        return packet;
    }
	
	
    
    public static void menu(CopycatClient client) throws Exception {

        int opcao = 0;
        String msg;
        BufferedReader mensagem;
        MapData map;
        mensagem = new BufferedReader(new InputStreamReader(System.in));
        Integer key = 0;

        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

        log.info("\n\n\n----------------MENU-----------------\n");
        System.out.println("Digite o número da operção desejada: ");
        System.out.println("1 - INSERT");
        System.out.println("2 - UPDATE");
        System.out.println("3 - DELETE");
        System.out.println("4 - SEARCH");
        System.out.println("5 - SAIR");
        System.out.println("OPTIONS: \n");

        opcao = scanner.nextInt();


        HashMap<Integer, String> mapa = new HashMap<Integer, String>();
        HashMap<String, String> data = new HashMap<String, String>();;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
		switch (opcao) {
            case 1:

                System.out.println("Digite a chave que deseja cadastrar:");
                key = scanner.nextInt();

                System.out.println("Digite a mensagem:");
                msg = mensagem.readLine();

                map = new MapData();
                mapa.put(key, msg);
                data.put("data", sdf.format(new Date(System.currentTimeMillis())));

                map.setMapa(mapa);
                map.setData(data);

                byte[] object = SerializationUtils.serialize(mapa);

                if (object.length > 1400) {
                    System.out.println("Pacote excede o tamanho padrão!");
                } else {
                    send(object);
                    CompletableFuture<Boolean> future = client.submit(new PostCommand(Long.valueOf(key), msg));
                    Object res = future.get();
                    System.out.println(String.valueOf(res));
                }

                break;
            case 2:
                System.out.println("Digite a key da mensagem a ser atualizada:");
                key = scanner.nextInt();

                System.out.println("Digite a Mensagem:");
                msg = mensagem.readLine();



                if (msg.length() > 1400) {
                    System.out.println("Pacote excede o tamanho padrão!");
                } else {
                    CompletableFuture<Object> future = client.submit(new GetCommand(key));
                    Object res = future.get();
                    System.out.println(String.valueOf(res));
                }

                break;
            case 3:
                System.out.println("Digite a key da mensagem a ser excluída:");
                key = scanner.nextInt();


                CompletableFuture<Object> future = client.submit(new GetCommand(key));
                Object res = future.get();
                System.out.println(String.valueOf(res));

                break;
            case 4:
                System.out.println("Digite a chave da mensagem que deseja recuperar:");
                key = scanner.nextInt();


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
}
