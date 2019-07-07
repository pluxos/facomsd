package state_machine.server;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


public class Kv {

  public Map<BigInteger, String> Database;

  public Kv () {
    this.Database = new HashMap<BigInteger, String>();
  }


  public Boolean Insert (BigInteger key, String value){
    if (this.Database.containsKey(key)){
      return false;
    } else {
      this.Database.put(key, value);
      return true;
    }
  }

  public String Read (BigInteger key) {
    if( this.Database.containsKey(key) ) {
      return this.Database.get(key);
    }
    else return null;
  }

  public Boolean Update (BigInteger key, String value) {
    if (this.Database.containsKey(key)) {
      this.Database.put(key, value);
      return true;
    } else {
      return false;
    }
  }

  public Boolean Delete (BigInteger key) {
    if (this.Database.containsKey(key)) {
      this.Database.remove(key);
      return true;
    } else {
      return false;
    }
  }
}
  