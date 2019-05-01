package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database extends ArquiveManipulation {

    static private Map<BigInteger, byte[]> map = new HashMap<>();
    static long count = 0;
    

    public Database() throws IOException {
    }
    
    public Database(boolean readFile) throws IOException {
        if(readFile){
            recuperaDados();
        }
    }
    //insere o vetor de bytes e retorna o id mapeado para o mesmo

    public long create(byte[] value) throws IOException {
        map.put(BigInteger.valueOf(count), value);
        return count++;
    }

    public byte[] read(BigInteger id) {
        return map.get(id);
    }

    public Map<BigInteger, byte[]> readAll() {
        return map;
    }

    public byte[] update(BigInteger id, byte[] value) throws IOException {
        return map.put(id, value);
    }

    public byte[] delete(BigInteger id) {
        return map.remove(id);
    }

    static public void recuperaDados() throws IOException {
        Database database = new Database();
        int aux = 0;

        String lerMetodo = "";
        String lerValor = "";
        String lerId = "";
        try {
            FileReader dados = new FileReader("data.txt");
            BufferedReader lerDados = new BufferedReader(dados);

            String linha = lerDados.readLine();   //Le a primeira linha

            while (linha != null) {
                for (int i = 0; i < linha.length(); i++) {
                    char c = linha.charAt(i);
                    aux = i;                     //para depois começar onde parou
                    if (c != ' ') //lendo o metodo
                    {
                        lerMetodo = lerMetodo + c;
                    } else {
                        break;
                    }
                }

                aux++;               // tira o espaço pra começar no caracter
                if ("create".equals(lerMetodo)) {
                    for (int i = aux; i < linha.length(); i++) {
                        char c = linha.charAt(i);             //le valor do parametro
                        lerValor = lerValor + c;              //adicionando um caracter na string
                    }
                    database.create(lerValor.getBytes());
                } else if ("update".equals(lerMetodo)) {
                    for (int i = aux; i < linha.length(); i++) {
                        char c = linha.charAt(i);
                        if (c != ' ') {
                            c = linha.charAt(i);             //lendo o id
                            lerId = lerId + c;
                        } else {
                            break;
                        }
                    }
                    aux++;                                     // tira o espaço pra começar no caracter
                    for (int i = aux; i < linha.length(); i++) {
                        char c = linha.charAt(i);             //lendo o parametro
                        lerValor = lerValor + c;
                    }

                    database.update(BigInteger.valueOf(Integer.parseInt(lerId)), lerValor.getBytes());
                } else if ("delete".equals(lerMetodo)) {
                    for (int i = aux; i < linha.length(); i++) {
                        char c = linha.charAt(i);            //le o id
                        lerId = lerId + c;
                    }
                    database.delete(BigInteger.valueOf(Integer.parseInt(lerId)));
                }

                aux = 0;
                lerMetodo = "";
                lerValor = "";
                lerId = "";
                linha = lerDados.readLine();
            }
            writer.close();

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            Database database = new Database();
            System.out.println(database.create("daniel marques".getBytes()));
            System.out.println(database.create("juliana alves".getBytes()));
            System.out.println(database.create("giovana marques".getBytes()));
            System.out.println(database.create("luiz antonio".getBytes()));
            
            System.out.println(database.readAll());
            //Iterator iterator = database.readAll().entrySet().iterator();
            
            for (int i = 0; i < Database.map.size(); i++) {
                System.out.println(new String(database.read(BigInteger.valueOf(i))));
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
