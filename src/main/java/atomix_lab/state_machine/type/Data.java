package atomix_lab.state_machine.type;

import java.io.Serializable;
import java.math.BigInteger;

public class Data implements Serializable {
    public BigInteger key;
    public byte [] value;

    public Data(BigInteger chave, byte [] valor) {
        this.key = chave;
        this.value = valor;
    }
    
    @Override
    public String toString()
    {
    	return "(" + key + "," + new String(value)  + ")";
    }
}
