package br.ufu.ds.client;

import br.ufu.ds.ServerProtocol;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * @author Marcus
 */
public abstract class MenuListener implements Runnable {

    private boolean run = true;
    private Scanner mScanner;

    public MenuListener() {
        this.mScanner = new Scanner(System.in);
    }

    public MenuListener(InputStream in) {
        this.mScanner = new Scanner(in);
    }

    @Override
    public final void run() {
        Thread.currentThread().setName("thread - MenuListener");

        System.out.println("Welcome! Sometimes, help can help you =D");
        String menu =
                "CREATE - Create new entry on server, with Key and Value\n" +
                "READ   - Read a Value from server with a provided Key\n" +
                "UPDATE - Udpdate a Value from server by Key\n" +
                "DELETE - Delete an Entry from server by Key\n" +
                "EXIT   - Shut down client\n\n";

        //System.out.println(menu);
        while (run) {
            String command = "exit";

            if (this.mScanner.hasNextLine()) {
                command = this.mScanner.nextLine();
            }

            command = command.trim();
            if (command.contains("create")) {

                try {
                    createOrUpdate(ServerProtocol.Request.RequestType.CREATE, command);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }

            } else if (command.contains("read")) {

                try {
                    readOrDelete(ServerProtocol.Request.RequestType.READ, command);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }

            } else if (command.contains("update")) {

                try {
                    createOrUpdate(ServerProtocol.Request.RequestType.UPDATE, command);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }

            } else if (command.contains("delete")) {

                try {
                    readOrDelete(ServerProtocol.Request.RequestType.DELETE, command);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }

            } else if (command.equals("exit")) {
                run = false;
                System.out.println("\nBye!");
            } else if (command.equals("help")) {
                System.out.println('\n' + menu);
             } else {
                System.err.println("Invalid command!");
            }
        }
    }

    protected abstract void onCreateSelected(BigInteger key, ByteString value);
    protected abstract void onReadSelected(BigInteger key);
    protected abstract void onUpdateSelected(BigInteger key, ByteString value);
    protected abstract void onDeleteSelected(BigInteger key);

    private void createOrUpdate(ServerProtocol.Request.RequestType type, String command) throws IOException {
        BigInteger key;
        ByteString value;

        String[] args = command.split(" ");

        if (args.length != 3) {
            throw  new IOException("Unexpected argument exception, expected: create/update [key:number] [value:any]");
        }

        try {
            key = BigInteger.valueOf(Long.valueOf(args[1].trim()));
        } catch (Exception ex) {
            throw new IOException("Key must be a number");
        }

        value = ByteString.copyFromUtf8(args[2].trim());

        if (type == ServerProtocol.Request.RequestType.CREATE) {
            onCreateSelected(key, value);
        } else if (type == ServerProtocol.Request.RequestType.UPDATE) {
            onUpdateSelected(key, value);
        }
    }

    private void readOrDelete(ServerProtocol.Request.RequestType type, String command) throws IOException {
        BigInteger key;

        String[] args = command.split(" ");

        if (args.length != 2) {
            throw  new IOException("Unexpected argument exception, expected: read/delete [key:number]");
        }

        try {
            key = BigInteger.valueOf(Long.valueOf(args[1]));
        } catch (Exception ex) {
            throw new IOException("Key must be a number");
        }

        if (type == ServerProtocol.Request.RequestType.READ) {
            onReadSelected(key);
        } else if (type == ServerProtocol.Request.RequestType.DELETE) {
            onDeleteSelected(key);
        }
    }
}
