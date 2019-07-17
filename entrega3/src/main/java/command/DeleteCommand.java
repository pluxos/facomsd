package command;

import io.atomix.copycat.Command;

public class DeleteCommand implements Command<Boolean>
{
    public int id;
    public byte[] value;
    
    //Construtor da classe DeleteCommand
    public DeleteCommand(int key) 
    {
        this.id = key;
    }
}
