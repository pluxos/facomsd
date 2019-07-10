package com.sd.projeto1.command;

import io.atomix.copycat.Command;

public class PutCommand implements Command<String>
{
    public Long id;
    public String message;

    public PutCommand(Long id, String message)
    {
        this.id = id;
        this.message = message;
    }
}
