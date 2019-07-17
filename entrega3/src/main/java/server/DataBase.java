package server;

import java.util.HashMap;
import java.util.Map;

import java.lang.*;

public class DataBase 
{
    private Map<Integer, byte[]> banco;

    public DataBase()
    {
        this.banco = new HashMap<Integer, byte[]>();
    }
    
    public Map<Integer, byte[]> getBanco()
    {
        return this.banco;
    }
    
    public Boolean Insert(Integer key, byte[] value) 
    {
        System.out.println("Insert <" + key + "," + new String(value) + ">");
        if (this.banco.containsKey(key)) 
        {
            return false;
        } 
        else 
        {
            this.toString();
            this.banco.put(key, value);
            return true;
        }
    }

    public byte[] Read(Integer key) 
    {
        System.out.println("Read <" + key + ">");
        if (this.banco.containsKey(key)) 
        {
            return this.banco.get(key);
        } 
        else
        {
            return null;
        }            
    }

    public Boolean Update(Integer key, byte[] value) 
    {
        System.out.println("Update <" + key + "," + new String(value) + ">");
        if (this.banco.containsKey(key)) 
        {
            this.banco.put(key, value);
            return true;
        } 
        else 
        {
            return false;
        }
    }

    public Boolean Delete(Integer key) 
    {
        System.out.println("Delete <" + key + ">");
        if (this.banco.containsKey(key)) 
        {
            this.banco.remove(key);
            return true;
        }
        else 
        {
            return false;
        }
    }

}
