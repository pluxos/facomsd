package br.ufu.ds.client;

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * @author Marcus
 */
public abstract class MenuListener implements Runnable {

    private boolean run = true;
    private Scanner mScanner;
    private int readDelay = 0;

    public MenuListener() {
        this.mScanner = new Scanner(System.in);
    }

    public MenuListener(InputStream in) {
        this.mScanner = new Scanner(in);
        try {
            FileOutputStream fout = new FileOutputStream("test_output");
            System.setOut(new PrintStream(fout));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        readDelay = 300;
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
            if (readDelay > 0) {
                try {
                    // for tests
                    Thread.sleep(readDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            String command = "exit";

            if (this.mScanner.hasNextLine()) {
                command = this.mScanner.nextLine();
            }

            command = command.trim();

            if (command.equals("exit")) {
                run = false;
                onExit();
                break;
            } else if (command.equals("help")) {
                System.out.println('\n' + menu);
                continue;
            } else {
                try {
                    if (newCommand(command)) {
                        continue;
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println("Invalid Command");
        }
    }

    protected abstract void onCreateSelected(BigInteger key, byte[] value);
    protected abstract void onReadSelected(BigInteger key);
    protected abstract void onUpdateSelected(BigInteger key, byte[] value);
    protected abstract void onDeleteSelected(BigInteger key);
    protected abstract void onExit();

    private boolean newCommand(String command) throws IOException {
        BigInteger key;
        String value;

        String[] args = command.split(" ");

        try {
            key = BigInteger.valueOf(Long.valueOf(args[1].trim()));
        } catch (Exception ex) {
            throw new IOException("Key must be a number");
        }

        if (args.length == 3) {
            value = args[2].trim();

            if (args[0].equals("create")) {
                onCreateSelected(key, value.getBytes());
            } else if (args[0].equals("update")) {
                onUpdateSelected(key, value.getBytes());
            } else {
                return false;
            }
        } else if (args.length == 2) {
            if (args[0].equals("delete")) {
                onDeleteSelected(key);
            } else if (args[0].equals("read")) {
                onReadSelected(key);
            } else {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }
}