package command;

import io.atomix.copycat.Command;

public class UpdateCommand implements Command<Boolean>
{
    public int id;
    public byte[] value;

    //Construtor da classe UpdateCommand
    public UpdateCommand(int key, String new_value) 
    {
        this.id = key;
        this.value = new_value.getBytes();
    }
}
