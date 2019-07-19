package com.SistemasDistribuidos.models;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * @author luizw
 *
 */
public class MapData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer,String> mapa;
	private HashMap<String,String> data;
	private Integer operation;
	
	public HashMap<Integer, String> getMapa() {
		return mapa;
	}
	public void setMapa(HashMap<Integer, String> mapa) {
		this.mapa = mapa;
	}
	public HashMap<String, String> getData() {
		return data;
	}
	public void setData(HashMap<String, String> data) {
		this.data = data;
	}
	public Integer getOperation() {
		return operation;
	}
	public void setOperation(Integer operation) {
		this.operation = operation;
	}
	
	
}
