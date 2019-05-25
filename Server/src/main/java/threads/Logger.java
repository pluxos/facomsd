package threads;


import model.ItemFila;
import singletons.F1;
import singletons.F2;
import singletons.F3;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.BlockingQueue;
import java.util.*;
import java.math.BigInteger;

public class Logger  implements Runnable
{
	protected BlockingQueue<ItemFila> f1;
	protected BlockingQueue<ItemFila> f2;
	protected BlockingQueue<ItemFila> f3;
	Path  path = Paths.get("./log");

	public Logger(){
		this.f1 = F1.getInstance();
		this.f2 = F2.getInstance();
		this.f3 = F3.getInstance();
	}

	@Override
	public void run() {
		try{
			getListOfCommands();
			while (true){
				ItemFila obj = f2.take();
				writeCommand(obj.toString());
			}
		}
		catch (InterruptedException ex){
			ex.printStackTrace();
		}
	}

	private void writeCommand(String comando){
		// if(comando == null) System.out.println("MEGA BATATA");
		if ( comando.substring( 0, 4 ).equals("READ") )
			return;
		try {
			if(!Files.exists(path)) {
				System.out.println("Arquivo Inexistente, Criando...");
				Files.createFile(path);
			}
			comando = comando + "\n";
			Files.write(Paths.get("log"), comando.getBytes(), StandardOpenOption.APPEND);

		}catch (IOException e) {
			System.out.println("IO Error");
		}
	}

	/*Essa é a parte de recuperação apartir do arquivo log*/
	public void getListOfCommands(){
		List<String> contents;
		ItemFila item;
		try{
			contents = Files.readAllLines(path);
			for(String content:contents){
				String[] commandSplited = content.split("\\s+");
				if ( commandSplited.length == 3 ){
					item = new ItemFila( null, commandSplited[0].getBytes(), new BigInteger( commandSplited[1] ).toByteArray(), commandSplited[2].getBytes());
					// item.print();
					f3.add(item);
				}
				else if ( commandSplited.length == 2 ){
					item = new ItemFila( null, commandSplited[0].getBytes(), new BigInteger( commandSplited[1] ).toByteArray() );
					// item.print();
					f3.add(item);
				}
			}
		}catch(Exception e){
			System.out.println( "Erro: " + e);
		}
		F1.setFree();
	}
}