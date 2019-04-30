// package com.SDgroup;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


public class Kv {
  
  public Map<BigInteger,byte[]> Database;
  
  public Kv () {
    this.Database = new HashMap<BigInteger, byte[]>(); //  Mudar o tipo das funções depois que passar pra Bytes[]
  }
  
  
  public Boolean Insert (BigInteger key, byte[] value){
    if (this.Database.containsKey(key)){
      return false;   // Se a chave já existir no banco, ele retorna falso
    } else {
      this.Database.put(key, value);  // Se não existir, ele insere a chave e retorna verdadeiro
      return true;
    }
  }
  
  public byte[] Read (BigInteger key) {
    return this.Database.get(key);
  }
  
  public Boolean Update (BigInteger key, byte[] value) {
    if (this.Database.containsKey(key)) {
      this.Database.put(key, value); //  Se a chave já existir, o valor dela é sobrescrito
      return true;
    } else {
      return false; //  Não existe a chave especificada
    }
  }
  
  public Boolean Delete (BigInteger key) {
    if (this.Database.containsKey(key)) {   // Se a chave existir ele remove e retorna verdadeiro, caso contrário, retorna falso
      this.Database.remove(key);
      return true;
    } else {
      return false;
    }
  }
  
  //  public static void main (String args[]) {
    //    Kv Data = new Kv();
    //    BigInteger number = new BigInteger("123");
    //    BigInteger number2 = new BigInteger("123");
    //
    //    System.out.println("2 Inserts");
    //    System.out.println(Data.Insert(number, "arroz".getBytes()));
    //    System.out.println(Data.Insert(number, "arroz".getBytes()));
    //    System.out.println("");
    //
    //    System.out.println("Read");
    //    System.out.println(new String(Data.Read(number)));
    //    System.out.println("");
    //
    //    System.out.println("Update");
    //    System.out.println(Data.Update(number, "batata".getBytes()));
    //    System.out.println("");
    //
    //    System.out.println("2 Deletes");
    //    System.out.println(Data.Delete(number));
    //    System.out.println(Data.Delete(number));
    //  }
    
  }
  