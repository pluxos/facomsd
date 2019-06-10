/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.math.BigInteger;

/**
 * A simple HelloWorldClient that requests a greeting from the {@link HelloWorldServer}.
 */
public class ClientGrpc {
  String welcome = "Esse software se baseia em um banco de dados chave valor.\n Chave: Inteiro(Infinito) \n Valor: Bytes\n";
  String options = "Opções disponivéis: \n- Create chave valor \n- Read chave \n- Update chave valor \n- Delete chave \n- Help \n- Sair \n";
  String read = "Digite uma opção válida: ";
  String invalid = "Opção inválida!!!";
  String done = "Conexão concluída!!!\n";
  String quit = "Conexão encerrada!!!";
  String close = "Saindo....";
  String option;
  String command;
  String key;
  String value;
  int size;
  int spaceFirst;
  int spaceSecond;

  BigInteger keyBigInteger;

  /** Construct ClientGrpc connecting to HelloWorld server at {@code host:port}. */
  public ClientGrpc(String host, int port) {

      // String value = "DEU CERTO";
      // int dez = 10;
      // BigInteger key = BigInteger.valueOf(dez);

      Scanner scanner = new Scanner( System.in );

      System.out.println( welcome );
      System.out.println( options );

      while( true ) {
        System.out.print( read );
        option = scanner.nextLine();

        try {
          option = option.toUpperCase();

          if( option.equals("SAIR") ) {
            System.out.println( close );
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

          if( ( command.equals( "CREATE" ) || command.equals( "UPDATE" ) ) &&  ( spaceSecond == -1 ) ) {
            throw new Exception();            
          }

          if( ( command.equals( "READ" ) || command.equals( "DELETE" ) ) && ( spaceSecond != -1 ) ) {
            throw new Exception();
          }

          key = option.substring( ( spaceFirst + 1 ), ( ( spaceSecond == -1 ) ? size : spaceSecond ) );
          keyBigInteger = new BigInteger( key );

          if( command.equals( "CREATE" ) ) {
            value = option.substring( ( spaceSecond + 1 ), size );
            byte[] messageBytesValue = value.getBytes();
            new Thread( new Create(host, port, keyBigInteger, messageBytesValue, true) ).start();
            continue;  
          }

          if( command.equals( "UPDATE" ) ) {
            value = option.substring( ( spaceSecond + 1 ), size );
            byte[] messageBytesValue = value.getBytes();
            new Thread( new Update(host, port, keyBigInteger, messageBytesValue, true) ).start();
            continue;
          }
          
          if( command.equals( "READ" ) ) {
            new Thread( new Read(host, port, keyBigInteger, true) ).start();
            continue;
          }
          
          if( command.equals( "DELETE" ) ) {
            new Thread( new Delete(host, port, keyBigInteger, true) ).start();
            continue;
          }

          throw new Exception();
        }
        catch(Exception e) {
          System.out.println( invalid );
        }
      }

      System.out.println(quit);
      scanner.close();
  }

  public static void main(String[] args) throws Exception {
    new ClientGrpc("localhost", 2000);     
  }
}
