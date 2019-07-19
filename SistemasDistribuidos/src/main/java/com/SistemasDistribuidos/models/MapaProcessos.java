/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SistemasDistribuidos.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author luizw
 *
 */
public class MapaProcessos implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<MapData> listaMapas;
    private MapData mapData;
    private String msg;
    
    public MapaProcessos() {
        mapData = new MapData();
        listaMapas = new ArrayList<>();
    }

	public List<MapData> getListaMapas() {
		return listaMapas;
	}

	public void setListaMapas(List<MapData> listaMapas) {
		this.listaMapas = listaMapas;
	}

	public MapData getMapData() {
		return mapData;
	}

	public void setMapData(MapData mapData) {
		this.mapData = mapData;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
