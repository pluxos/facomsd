package com.SistemasDistribuidos.servidor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Server extends Thread {

	private static ArrayList<BufferedWriter>clientes;           
	private static ServerSocket server; 
	private String nome;
	private Socket con;
	private InputStream in;  
	private InputStreamReader inr;  
	private BufferedReader bfr;
	private int v[] = new int[10];
	private int i = 0;
	Logger logger = Logger.getLogger("Teste");
	
	/**
	  * Método construtor 
	  * @param com do tipo Socket
	  */
	public Server(Socket con){
	   this.con = con;
	   try {
	         in  = con.getInputStream();
	         inr = new InputStreamReader(in);
	          bfr = new BufferedReader(inr);
	   } catch (IOException e) {
	          e.printStackTrace();
	   }                          
	}
  
	/**
	  * Método run
	  */
	public void run(){
	                       
	  try{
		  
		  logger.info("iniciando aplicação");
		 
	                                      
	    String msg;
	    OutputStream ou =  this.con.getOutputStream();
	    Writer ouw = new OutputStreamWriter(ou);
	    BufferedWriter bfw = new BufferedWriter(ouw); 
	    clientes.add(bfw);
	    nome = msg = bfr.readLine();
	               
	    while(!"Sair".equalsIgnoreCase(msg) && msg != null)
	      {           
	       msg = bfr.readLine();
	       sendToAll(bfw, msg);
	       System.out.println(msg);                                              
	       }
	                                      
	   }catch (Exception e) {
	     e.printStackTrace();
	    
	   }                       
	}
	
	/***
	 * Método usado para enviar mensagem para todos os clients
	 * @param bwSaida do tipo BufferedWriter
	 * @param msg do tipo String
	 * @throws IOException
	 */
	public void sendToAll(BufferedWriter bwSaida, String msg) throws  IOException 
	{
	 logger.info("Enviando para todos");
	  BufferedWriter bwS;
	  logger.info("iniciando aplicação");
	  for(BufferedWriter bw : clientes){
	   bwS = (BufferedWriter)bw;
	   if(!(bwSaida == bwS)){
	     bw.write(nome + " -> " + msg+"\r\n");
	     bw.flush(); 
	   }
	  }          
	}
	public void createVetor(BufferedWriter bwSaida, String msg) throws  IOException 
	{
		 logger.info("Criando Vetor");
	  BufferedWriter bwS;	    
	  for(BufferedWriter bw : clientes){
	   bwS = (BufferedWriter)bw;
	   if(!(bwSaida == bwS)){
		  v[i]=Integer.parseInt(msg);
		  i++;
	     bw.write(nome + " -> Foi inserido um elemento"+ msg +" \r\n");
	     bw.flush(); 
	   }
	  }          
	}
	public void updateVetor(BufferedWriter bwSaida, String msg) throws  IOException 
	{
		 logger.info("Atualizando Vetor");
	  BufferedWriter bwS;
	    
	  for(BufferedWriter bw : clientes){
	   bwS = (BufferedWriter)bw;
	   if(!(bwSaida == bwS)){
		 
	     bw.write(nome + " -> o vetor "+i +" foi atualizado para" + msg+"\r\n");
	     v[i]=Integer.parseInt(msg);
	     bw.flush(); 
	   }
	  }          
	}
	public void deleteVetor(BufferedWriter bwSaida, String msg) throws  IOException 
	{
		 logger.info("deletando vetor");
	  BufferedWriter bwS;
	    
	  for(BufferedWriter bw : clientes){
	   bwS = (BufferedWriter)bw;
	   if(!(bwSaida == bwS)){
	     bw.write(nome + " -> deletando o valor " + v[i]+"\r\n");
	     i--;
	     bw.flush(); 
	   }
	  }          
	}
	public void readVetor(BufferedWriter bwSaida, String msg) throws  IOException 
	{
		 logger.info("lendo vetores");
	  BufferedWriter bwS;
	  int j;
	    
	  for(BufferedWriter bw : clientes){
	   bwS = (BufferedWriter)bw;
	   if(!(bwSaida == bwS)){
		 for(j=0;j<=i;j++) {
			 bw.write(nome + " : " + v[i]+"\r\n");
		     bw.flush(); 
		 }
	    
	   }
	  }          
	}
	
	/***
	   * Método main
	   * @param args
	   */
	public static void main(String []args) {
	    
	  try{
	    //Cria os objetos necessário para instânciar o servidor
	    JLabel lblMessage = new JLabel("Porta do Servidor:");
	    JTextField txtPorta = new JTextField("12345");
	    Object[] texts = {lblMessage, txtPorta };  
	    JOptionPane.showMessageDialog(null, texts);
	    server = new ServerSocket(Integer.parseInt(txtPorta.getText())); 
	    clientes = new ArrayList<BufferedWriter>();
	    JOptionPane.showMessageDialog(null,"Servidor ativo na porta: "+         
	    txtPorta.getText());
	    
	     while(true){
	       System.out.println("Aguardando conexão...");
	       Socket con = server.accept();
	       System.out.println("Cliente conectado...");
	       Thread t = new Server(con);
	        t.start();   
	    }
	                              
	  }catch (Exception e) {
	    
	    e.printStackTrace();
	  }                       
	 }// Fim do método main                      
	} //Fim da classe



