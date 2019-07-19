package com.SistemasDistribuidos.utils;

import io.atomix.copycat.Command;

public class PostCommand implements Command<Boolean> {

	private static final long serialVersionUID = 1L;

	public Long id;
	public String message;

	public PostCommand(Long id, String message) {
		this.id = id;
		this.message = message;
	}
}
