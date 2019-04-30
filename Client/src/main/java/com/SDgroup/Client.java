// package com.SDgroup;
import java.io.*;
import java.net.*;
import java.util.*;
import java.math.BigInteger;

public class Client {
  String welcome = "Esse software se baseia em um banco de dados chave valor.\n Chave: Inteiro(Infinito) \n Valor: Bytes\n";
  String options = "Opções disponivéis: \n- Create chave valor \n- Read chave \n- Update chave valor \n- Delete chave \n- Help \n- Sair \n";
  String read = "Digite uma opção válida: ";
  String invalid = "Opção inválida!!!";
  String done = "Conexão concluída!!!\n";
  String quit = "Conexão encerrada!!!";
  String close = "Saindo...";
  String option;
  String command;
  String key;
  String value;

  int size;
  int spaceFirst;
  int spaceSecond;

  int quantityPackage; // 1 for 2 or 4 for 3

  BigInteger keyBigInteger;

  Socket connection;

  public Client() {
    try {
      connection = new Socket( "localhost", 12345 );
      System.out.println( done );

      Runnable runnable = new ClientResponse(connection);
      Thread thread = new Thread(runnable);
      thread.start();

      Scanner scanner = new Scanner( System.in );
      DataOutputStream output = new DataOutputStream( connection.getOutputStream() );

      System.out.println( welcome );
      System.out.println( options );
      while( true ) {
        System.out.print( read );
        option = scanner.nextLine();

        try {
          option = option.toUpperCase();
          quantityPackage = 1;

          if( option.equals("SAIR") ) {
            System.out.println( close );
            byte[] messageBytesCommand = option.getBytes();
            output.writeInt( ( ( messageBytesCommand.length*10 ) + 5 ) );
            output.write( messageBytesCommand );
            Thread.sleep(5000);
            break;
          }

          if( option.equals("HELP") ) {
            System.out.println( options );
            continue;
          }

          size = option.length();
          spaceFirst = option.indexOf(" ");

          if( spaceFirst == -1 ) {
            throw new Exception();
          }

          spaceSecond = option.indexOf( " ", ( spaceFirst + 1 ) );
          command = option.substring( 0, spaceFirst );

          if( command.equals( "CREATE" ) || command.equals( "UPDATE" )  ) {
            quantityPackage = 4;
            if( spaceSecond == -1 ) {
              throw new Exception();
            }
          }
          
          if( ( command.equals( "READ" ) || command.equals( "DELETE" ) ) && ( spaceSecond != -1 ) ) {
            throw new Exception();
          }

          key = option.substring( ( spaceFirst + 1 ), ( ( spaceSecond == -1 ) ? size : spaceSecond ) );
          keyBigInteger = new BigInteger( key );
          
          byte[] messageBytesCommand = command.getBytes();
          output.writeInt( ( ( messageBytesCommand.length*10 ) + quantityPackage ) );
          output.write( messageBytesCommand );
          
          byte[] messageBytesKey = keyBigInteger.toByteArray();
          output.writeInt( ( ( messageBytesKey.length*10 ) + 2 ) );
          output.write( messageBytesKey );

          if( spaceSecond != -1 ) {
            value = option.substring( ( spaceSecond + 1 ), size );
            byte[] messageBytesValue = value.getBytes();
            output.writeInt( ( ( messageBytesValue.length*10 ) + 3 ) );
            output.write( messageBytesValue );
          }
        }
        catch(Exception e) {
          System.out.println( invalid );
        }
      }

      output.close();
      System.out.println(quit);
    }
    catch(Exception e) {
      System.out.println( "Erro: " + e.getMessage() );
    }
  }
  public static void main(String argv[]) throws Exception {

    new Client();
  }
}
