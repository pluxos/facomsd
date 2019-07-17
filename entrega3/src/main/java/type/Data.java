package type;

import java.io.Serializable;

public class Data implements Serializable 
{
    public int id;
    public byte[] value;

    public Data (int key, byte[] value) 
    {
        this.id = key;
        this.value = value;
    }
    
    @Override
    public String toString()
    {
    	return "[" + id + "," + value + "]";
    }

}