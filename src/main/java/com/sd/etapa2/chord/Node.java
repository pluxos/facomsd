package com.sd.etapa2.chord;

import java.io.Serializable;
import java.util.Vector;

//Node do chord que representa um nó no anel
//Nao completo
public class Node implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int porta;
	private Integer[] fingerTable;
	private Vector<Integer> antecessores;
	private int m;
	

	public Node(Integer id,int m) {
		this.id = id;
		this.m = m;
		fingerTable = new Integer[m];
		this.antecessores = new Vector<>();
	}

	public void insereAntecessor(Integer antecessor) {
		antecessores.add(antecessor);	
	}
	
	public void removeAntecessor(Integer antecessor) {		
		antecessores.remove(antecessor);
	}

	public void imprimeAntecessores() {
		System.out.print("Node: " + id + " Antecessores: [");
		for (Integer antecessor : antecessores) {
			System.out.print(antecessor+" ");
		}
		System.out.print("]");
	}
	
	public synchronized Vector<Integer> getAntecessores(){
		return this.antecessores;
	}
	
	public int getId() {
		return id;
	}

	public void fingerTable(Integer linha, int pos) {
		this.fingerTable[pos] = linha;
	}
	
	public void limpaFingerTable() {
		for(int i = 0; i < fingerTable.length; i++) {
			this.fingerTable[i] = null;
		}
	}
	
	public void imprimeFingerTable() {
		System.out.println("Node ID: " + this.id);
		for(int i = 0; i<fingerTable.length;i++) {
			System.out.println("ID " + (i+1) + " - Sucessor " + fingerTable[i]);
		}
	}
	
	public boolean possuiAntecessor(Integer antecessor) {
		if(antecessores.contains(antecessor) ) {
			return true;
		} else return false;
	}
	
	public Integer[] getFingerTable() {
		return this.fingerTable;
	}

	
	@Override
	public boolean equals(Object obj) {
		Node node = (Node) obj;
		return node.getId() == this.id;
	}

	public void setId(Integer id) {
		this.id = id;
		
	}	

	public void setPorta(int porta) {
		this.porta = porta;
	}
	public int getPorta() {
		return porta;
	}
	
	public void setAntecessores(Vector<Integer> antecessores) {
		this.antecessores = antecessores;
	}
	
	public boolean ehReponsavel(Integer chave){
		return possuiAntecessor(chave);
	}
}


