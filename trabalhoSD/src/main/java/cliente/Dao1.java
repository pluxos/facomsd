package cliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import servidor.Dados;

//public class Dao1 implements DAO {
public class Dao1 {
  public Dados get(Long key) {
    Dados d = null;
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    try {
      socket = new Socket("127.0.0.1", 9876);
      out = new ObjectOutputStream(socket.getOutputStream());
      in = new ObjectInputStream(socket.getInputStream());
      out.writeObject(new Long(key));
      d = (Dados) in.readObject();
      out.writeObject(new Long(0L));
      socket.close();
      out.close();
      in.close();
    } catch (Exception e) {
    }
    return d;
  }
  
  public Object get(Object key) {
    // TODO Auto-generated method stub
    return null;
  }
}
