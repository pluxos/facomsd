package server;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logger implements Runnable
{
	private BlockingQueue<Operation> f1;
    private BlockingQueue<Operation> f2;
	private BlockingQueue<Operation> f3;

	private File arquivo;
	private String id;
	private int number = 0;
	Comunication com;
	Snapshot snap;
	
	public Logger()
	{

	}

	public void makeFile() 
	{
		try 
		{
            File diretorio = new File("snap_" + this.number);
            diretorio.mkdir();
		} 
		catch (Exception ex) 
		{
            System.out.println(ex.getMessage());
        }
	}

	public void run() 
	{
		try
		{
			while (true) 
			{
				com.run();
				Operation c;
				try 
				{
					c = f2.take();
	
					String comando = c.getOperation();
					String comandos[] = comando.split(" ");
					int number_snap = this.snap.getIdFile();
	
					File diretorio = new File("LOG_" + this.id);
					if (!diretorio.exists()) {
						this.makeFile();;
					}
	
					
					if (this.number <= number_snap) {
	
						this.number = number_snap + 1;
	
						if (this.c.size() == 3) {
							int menor = this.getMenor();
							File arq_remover = new File("LOG_" + this.id + "//" + "Log" + menor + ".txt");
							if (arq_remover.exists()) {
								arq_remover.delete();
							}
	
							this.c.remove(getPos(menor));
						}
						this.c.add(this.number);
	
					}
					this.arquivo = new File("LOG_" + this.id + "//" + "Log" + this.number + ".txt");
	
					if (!this.arquivo.exists()) {
						try {
							this.arquivo.createNewFile();
						} catch (IOException ex) {
							Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
	
					FileWriter fw;
					try {
						FileWriter arq = new FileWriter(this.arquivo, true);
						PrintWriter gravarArq = new PrintWriter(arq);
	
						if (!comandos[0].toLowerCase().equals("select") || !comandos[0].equals("quit")) {
							if (comandos[0].toLowerCase().equals("delete")) {
								gravarArq.println(comando + " " + c.getKey());
							} else if (comandos[0].toLowerCase().equals("update")
									|| comandos[0].toLowerCase().equals("insert")) {
								gravarArq.println(comando + " " + c.getKey() + " " + c.getValue());
							}
						}
						gravarArq.flush();
						arq.close();
	
					} catch (IOException ex) {
						Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
					}
	
					c = null;
					comando = null;
					comandos = null;
					System.gc();
					com.finalRead();
				} 
				catch (InterruptedException ex) 
				{
					Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}