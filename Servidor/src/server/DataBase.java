package server;

import java.io.*;
import java.math.BigInteger;
import java.util.*;


public class DataBase {
    
    static private Map<BigInteger, byte[]> mapa = new HashMap<>();
    static long cont = 0;
    

    public DataBase() throws IOException {
    }
    
    public DataBase(boolean readFile) throws IOException {
        if(readFile){
            rData();
        }
    }
    

    public long create(byte[] value) throws IOException {
        mapa.put(BigInteger.valueOf(cont), value);
        return cont++;
    }

    public byte[] read(BigInteger id) {
        return mapa.get(id);
    }
    
     public Map<BigInteger, byte[]> readallFile() {
        return mapa;
    }

    public byte[] update(BigInteger id, byte[] value) throws IOException {
        return mapa.put(id, value);
    }

    public byte[] delete(BigInteger id) {
        return mapa.remove(id);
    }

    static public void rData() throws IOException {
        DataBase database = new DataBase();
        int aux = 0;
        String lMetodo = "";
        String lValor = "";
        String lId = "";
        
        try {
            FileReader dados = new FileReader("data.txt");
            BufferedReader lerDados = new BufferedReader(dados);

            String line = lerDados.readLine();  

            while (line != null) {
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    aux = i;                     
                    if (c != ' ')           
                    {
                        lMetodo = lMetodo + c;
                    } else {
                        break;
                    }
                }

                aux++;               
                if (null != lMetodo) switch (lMetodo) {
                    case "create":
                        for (int i = aux; i < line.length(); i++) {
                            char c = line.charAt(i);
                            lValor = lValor + c;
                        }   database.create(lValor.getBytes());
                        break;
                    case "update":
                        for (int i = aux; i < line.length(); i++) {
                            char c = line.charAt(i);
                            if (c != ' ') {             
                                c = line.charAt(i);
                                lId = lId + c;
                            } else {
                                break;
                            }
                        }   aux++;
                        for (int i = aux; i < line.length(); i++) {
                            char c = line.charAt(i);
                            lValor = lValor + c;
                        }   database.update(BigInteger.valueOf(Integer.parseInt(lId)), lValor.getBytes());
                        break;
                    case "delete":
                        for (int i = aux; i < line.length(); i++) {
                            char c = line.charAt(i);
                            lId = lId + c;
                        }   database.delete(BigInteger.valueOf(Integer.parseInt(lId)));
                        break;
                    default:
                        break;
                }

                aux = 0;
                lMetodo = "";
                lValor = "";
                lId = "";
                line = lerDados.readLine();
            }
     

        } catch (IOException e) {
            System.err.printf("Erro no arquivo: %s.\n", e.getMessage());
        }
    }      

}