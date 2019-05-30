package com.sd.app;

/**
 *
 * @author Douglas
 */
import java.io.*;
import java.net.*;
import java.util.*;
import java.math.BigInteger;

public class Cliente {
 

  String opcao;
  String operacao;
  String chave;
  String valor;
  boolean visivel;

  int tamanho;
  int espaco1;
  int espaco2; 
  int espaco3;


  BigInteger keyBigInteger;

  Socket connection;

  public Cliente() {
    try {
      connection = new Socket( "localhost", 4000 );
      System.out.println( "CONECTADO COM SUCESSO! \n" );
      visivel = true;

      Runnable runnable = new ClienteMsg(connection);
      Thread thread = new Thread(runnable);
      thread.start();

      Scanner scanner = new Scanner( System.in );
      DataOutputStream output = new DataOutputStream( connection.getOutputStream() );

      
      while( true ) {
        if(visivel){
          System.out.println( "\n| Create chave valor\n| Read chave\n| Update chave valor\n| Delete chave\n| Sair \n");
          System.out.println( "ESCOLHA => " );
          opcao = scanner.nextLine();
  
        }
    
        try {
        	opcao = opcao.toUpperCase();

          if( opcao.equals("SAIR") ) {
            System.out.println( "FECHANDO A CONEXÃO" );
            output.writeUTF(opcao);
           Thread.sleep(5000);//espera 5s por novas msg do 
            break;
          }

          tamanho = opcao.length();
          espaco1 = opcao.indexOf(" ");

          if( espaco1 == -1 ) {
            throw new Exception();
          }

          espaco2 = opcao.indexOf( " ", ( espaco1 + 1 ) );
          operacao = opcao.substring( 0, espaco1 );

          if( operacao.equals( "CREATE" ) || operacao.equals( "UPDATE" )  ) {
            if( espaco2 == -1 ) {
              throw new Exception();
            }
          }
          
          if( ( operacao.equals( "READ" ) || operacao.equals( "DELETE" ) ) && ( espaco2 != -1 ) ) {
            throw new Exception();
          }

          chave = opcao.substring( ( espaco1 + 1 ), ( ( espaco2 == -1 ) ? tamanho : espaco2 ) );
          keyBigInteger = new BigInteger( chave );
              
          boolean tem = false;
          if( espaco2 != -1 ) {
            espaco3 = opcao.indexOf( " ", ( espaco2 + 1 ) );
        	  if(espaco3 != -1)
        		  throw new Exception();
        	  
            valor = opcao.substring( ( espaco2 + 1 ), tamanho );
            tem = true;
          }
          
          output.writeUTF(opcao);
          output.writeUTF(chave);
          if(tem) output.writeUTF( valor );

                    
        }
        catch(Exception e) {
          System.out.println( "ERROR => OPCAO INVALIDA " );
        }
      }
      scanner.close();
      output.close();
       System.out.println(" CONEXÃO ENCERRADA !");
    }
    catch(Exception e) {
      System.out.println( "Erro: " + e.getMessage() );
    }
  }
  
  public static void main(String argv[]) throws Exception {

    new Cliente();
  }
}