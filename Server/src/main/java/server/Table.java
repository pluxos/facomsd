package server;

import java.io.IOException;
import java.lang.Integer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Table {
    public String[][] table;
    public int myKey;
    public String myIP;
    public int myPort;

    // Colunas: 0 - Key Esperado(p + 2^(i-1)), 1 - Key, 2 - IP, 3 - Port
    public int qtColunas = 4;

    private Table(int qtLinhas, int myKey, String myIP, int myPort) {
        this.myKey = myKey;
        this.myIP = myIP;
        this.myPort = myPort;
        // Check If Directory Already Exists Or Not?
        Path dirPathObj = Paths.get(Integer.toString(myKey));
        if (!Files.exists(dirPathObj)) {
            try {
                // Creating The New Directory Structure
                Files.createDirectories(dirPathObj);
                System.out.println("! New Directory Successfully Created !");
            } catch (IOException ioExceptionObj) {
                System.out.println(
                        "Problem Occured While Creating The Directory Structure= " + ioExceptionObj.getMessage());
            }
        }

        int potencia;
        int keyEsperado;
        String zero = "0";

        this.table = new String[qtLinhas][this.qtColunas];
        for (int linha = 0; linha < qtLinhas; linha++) {
            for (int coluna = 1; coluna < this.qtColunas; coluna++) {
                this.table[linha][coluna] = zero;
            }

            // Calcula a key esperado
            potencia = 0;
            for (int i = 0; i < linha; i++) {
                potencia = potencia + 2;
            }
            if (potencia == 0) {
                potencia = 1;
            }
            keyEsperado = (myKey + potencia);
            this.table[linha][0] = keyEsperado + "";
        }
    }

    public int getMyKey() {
        return myKey;
    }

    private static Table ourInstance;

    public static Table getInstance() {
        if (ourInstance == null) {
            System.out.println("TABELA NÃO INSTANCIADA");
        }
        return ourInstance;
    }

    public static void createInstance(int myKey, String myIP, int myPort) {
        if (ourInstance == null) {
            // Qt de servidores na tabela, pegar no Proto - É A QUANTIDADE DE LINHAS
            int qtLinhas = 6;

            ourInstance = new Table(qtLinhas, myKey, myIP, myPort);
        } else {
            System.out.println("TABELA JÁ INSTANCIADA");
        }
    }

    public void imprimir() {
        for (int linha = 0; linha < this.table.length; linha++) {
            System.out.println(
                    "Linha " + (linha + 1) + ": " + this.table[linha][0] + "(Key Esperado) | " + this.table[linha][1]
                            + "(Key) | " + this.table[linha][2] + "(IP) | " + this.table[linha][3] + "(Port)");
        }
    }

    public void setTable(int newNum, String newIP, int newPort) {
        for (int linha = 0; linha < this.table.length; linha++) {
            this.table[linha][1] = newNum + "";
            this.table[linha][2] = newIP;
            this.table[linha][3] = newPort + "";
        }
        this.imprimir();
    }

    public void insertTable(int newNum, String newIP, int newPort, int linha) {
        boolean imprimirTabela = true;

        this.table[linha][1] = newNum + "";
        this.table[linha][2] = newIP;
        this.table[linha][3] = newPort + "";

        for (int i = 0; i < this.table.length; i++) {
            int num = Integer.parseInt(this.table[i][1]);
            if (num == 0) {
                imprimirTabela = false;
            }
        }

        if (imprimirTabela) {
            this.imprimir();
        }
    }

}