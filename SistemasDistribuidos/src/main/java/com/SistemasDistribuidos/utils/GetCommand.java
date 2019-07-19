package com.SistemasDistribuidos.utils;

import io.atomix.copycat.Query;

/**
 * 
 * @author luizw
 *
 */
public class GetCommand implements Query<Object> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Integer id;

    public GetCommand(Integer key) {
        this.id = key;
    }
}
