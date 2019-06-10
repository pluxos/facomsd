package cliente;

//import configuration.ApplicationProperties;
import model.Menu;
import threads.ThreadProcess;

import java.math.BigInteger;
import java.util.Scanner;
//import java.util.logging.Logger;

public class Main {
    public static void main(String args[]) {

    	  // String port = ApplicationProperties.getInstance().
    	  // loadProperties().getProperty("server.port");
    	  // Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    	  // String IPADDRESS = "localhost";


    	
      
        Scanner scanner = new Scanner(System.in);

        //------- ATRIBUTOS A SEREM ENVIADOS
        Integer opcaoMenu = 4;
        BigInteger chave;
        String valor = null;
        //----------------------------------
        while (true) {
            //------- MENU
            opcaoMenu = imprimirMenu(opcaoMenu, scanner);
            //----------------------------------
            if(opcaoMenu==10)
                break;

            //------- RECEBER CHAVE E VALOR
            System.out.println("--------------------");
            System.out.println("Digite a chave:");
            chave = scanner.nextBigInteger();

            if (opcaoMenu == Menu.CREATE.getValor() || opcaoMenu == Menu.UPDATE.getValor()) {
                System.out.println("Digite o valor ");
                valor = scanner.next();
            }
            //----------------------------------

            //------- CRIACAO DE THREADS
            Thread t = new ThreadProcess(chave, valor, opcaoMenu);
            t.start();
            //----------------------------------
        }

    }

    public static int imprimirMenu(int opcao, Scanner scanner){
        do {
            Menu[] menu = Menu.values();
            for (Menu m : menu) {
                System.out.printf("[%d] - %s%n", m.ordinal(), m.name());
            }
            System.out.println("Opção:");
            opcao = scanner.nextInt();
        }while ((opcao > 3 || opcao < 0) && opcao != 10);
        return opcao;
    }

}