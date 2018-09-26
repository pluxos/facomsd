package com.client.impl.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import com.client.ClientException;
import com.client.DataStoreClient;
import com.client.impl.DataStoreClientImpl;

public class Client {

    static byte[] addr = {127, 0, 0, 1};
    static int port = 10024;

    public static void main(String[] args) {

        try {
            Scanner s = new Scanner(System.in);
            String opcao = "";
            InetAddress inetAddress = InetAddress.getByAddress(addr);
            DataStoreClient dataStoreClient = new DataStoreClientImpl(inetAddress,
                    port);

            while (true) {

                System.out.println(" -------------- MENU ------------  ");
                System.out.println("|             C - CREATE	  |");
                System.out.println("|             R - READ		  |");
                System.out.println("|             D - DELETE	  |");
                System.out.println("|             E - EXIT		  |");
                System.out.println(" --------------------------------  ");

                System.out.println(" > Digite a opção: ");
                opcao = s.nextLine();

                if ((opcao.equals("C"))) {

                    System.out.println("Digite o valor: ");
                    Scanner t = new Scanner(System.in);
                    String valor = t.nextLine();

                    byte[] data = new byte[100];
                    Random random = new Random();
                    random.nextBytes(data);

                    dataStoreClient.write(valor, data);
                }
                if ((opcao.equals("R"))) {

                    System.out.println("Digite o valor: ");
                    Scanner t = new Scanner(System.in);
                    String valor = t.nextLine();

                    byte[] data = new byte[100];
                    Random random = new Random();
                    random.nextBytes(data);

                    dataStoreClient.read(valor);


                }
                if ((opcao.equals("D"))) {

                    System.out.println("Digite o valor: ");
                    Scanner t = new Scanner(System.in);
                    String valor = t.nextLine();

                    dataStoreClient.delete(valor);
                }
                if ((opcao.equals("E"))) {
                    System.exit(0);
                }
            }

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
