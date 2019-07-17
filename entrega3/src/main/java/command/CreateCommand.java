package command;

import io.atomix.copycat.Command;

public class CreateCommand implements Command<Boolean>
{
    public int id;
    public byte[] value;

    //Construtor da classe CreateCommand
    public CreateCommand(int key, String value) 
    {
        this.id = key;
        this.value = value.getBytes();
    }
}
