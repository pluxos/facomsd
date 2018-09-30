package application.Cliente;

import application.Menu;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;

public class MenuThread extends Thread {

    private Socket socket;
    private String usuario;
    private BigInteger key;
    private String message;
    private String code;


    @Override
    public void run() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            //--Montando requisição---
            DataOutputStream outToServer = null;
            try {
                outToServer = new DataOutputStream(this.getSocket()
                        .getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String request;

            if (this.getUsuario().equals("manual")) {
                request = InsertMenu(scan);
            } else {
                request = AutomaticMenu();
            }


            //Enviando o pacote contendo os bytes dos comandos para o servidor.
            try {
                outToServer.writeBytes(request + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(5000l);
            } catch (InterruptedException e) {

            }

            Thread.interrupted();

        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public BigInteger getKey() {
        return key;
    }

    public void setKey(BigInteger key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static String InsertMenu(Scanner scan) {
        menu();
        String code = chooseOption();
        System.out.print("Key: ");
        BigInteger key = scan.nextBigInteger();
        String message = readMessage(code);
        String request = code + "'" + key + "'" + message + "'";
        return request;
    }

    public String AutomaticMenu() {
        BigInteger key = getKey();
        String message;
        String code = getCode();
        if (code.equals("1") || code.equals("3")) {
            message = getMessage();
        } else {
            message = " ";
        }

        String request = code + "'" + key + "'" + message + "'";
        return request;
    }

    public static String readMessage(String code) {
        Scanner scan = new Scanner(System.in);
        String M;
        if (code.equals("1") || code.equals("3")) {
            System.out.print("Valor: ");
            M = scan.nextLine();
            return M;
        }
        return " ";
    }

    public static void menu() {
        Menu[] menu = Menu.values();
        System.out.println("----Escolha um código---");
        for (Menu m : menu) {
            System.out.printf("%d) %s%n", m.ordinal() + 1, m.name());

        }
        System.out.print("Option: ");
    }

    public static String chooseOption() {
        int code = 0;
        Scanner scan = new Scanner(System.in);
        code = scan.nextInt();
        if (code == 5) {
            Thread.currentThread().stop();
        }


        while (code < 1 || code > 4) {
            System.out.println("----Escolha um opção válida----");
            menu();
            code = scan.nextInt();
        }
        return Integer.toString(code);
    }

}
