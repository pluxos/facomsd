package com.sd.projeto1.command;

import io.atomix.copycat.Command;

public class DeleteCommand implements Command<String> {
    public Long id;

    public DeleteCommand(Long id) {
        this.id = id;
    }
}
