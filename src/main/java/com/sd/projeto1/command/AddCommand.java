package com.sd.projeto1.command;

import io.atomix.copycat.Command;

public class AddCommand implements Command<String>
{
    public Long id;
    public String message;

    public AddCommand(Long id, String message)
    {
        this.id = id;
        this.message = message;
    }
}
