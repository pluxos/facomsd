package atomix_lab.state_machine.type;

import java.io.Serializable;
import java.math.BigInteger;

public class Data implements Serializable {
    BigInteger key;
    byte [] value;

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
