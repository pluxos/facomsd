package command;

import type.Data;
import io.atomix.copycat.Query;

public class ReadCommand implements Query<Data>
{
    public int id;

    //Construtor da classe ReadCommand
    public ReadCommand(int key) 
    {
        this.id = key;
    }
}
