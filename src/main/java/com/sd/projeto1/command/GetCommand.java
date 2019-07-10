package com.sd.projeto1.command;

import io.atomix.copycat.Query;

public class GetCommand implements Query<String> {
    public Long id;

    public GetCommand(Long id) {
        this.id = id;
    }
}
