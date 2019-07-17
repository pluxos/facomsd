package server;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Snapshot implements Runnable
{
	private DataBase banco;
	private Comunication con;
	private int number;
	private int id_file = 0;
	private File arquivo;

	public Snapshot(DataBase banco, Comunication con, int n)
	{
		this.banco = banco;
		this.con = con;
		this.number = n;
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
	
	public int getIdFile() 
	{
        return this.id_file;
    }

	public void run() 
	{
		try
		{
			while(true)
			{
				File diretorio = new File("SNAP_" + this.number);
				if (!diretorio.exists()) {
					this.makeFile();
				}
				try 
				{
					Thread.sleep(3000);
				} 
				catch (InterruptedException ex) 
				{
					Logger.getLogger(Snapshot.class.getName()).log(Level.SEVERE, null, ex);
				}
				
				File arq_remover;
				this.arquivo = new File("SNAP_" + this.number + "//" + "Snapshot" + id_file + ".json");
				int id_file_remove = id_file - 3;
	
				if (id_file % 3 == 0 && id_file >= 3) 
				{
					arq_remover = new File("SNAP_" + this.number + "//" + "Snapshot" + id_file_remove + ".json");
					if (arq_remover.exists()) 
					{
						arq_remover.delete();
					}
				} 
				else if (id_file % 3 == 1 && id_file >= 3)
				 {
					arq_remover = new File("SNAP_" + this.number + "//" + "Snapshot" + id_file_remove + ".json");
					if (arq_remover.exists()) 
					{
						arq_remover.delete();
					}
				} 
				else if (id_file % 3 == 2 && id_file >= 3) 
				{
					arq_remover = new File("SNAP_" + this.number + "//" + "Snapshot" + id_file_remove + ".json");
					if (arq_remover.exists()) 
					{
						arq_remover.delete();
					}
				}
				
				if (!this.arquivo.exists()) 
				{
					try 
					{
						this.arquivo.createNewFile();
					} 
					catch (IOException ex) 
					{
						Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
				
				FileWriter arqDados = null;
				try 
				{
					arqDados = new FileWriter(this.arquivo, false);
				}
				catch (IOException ex) 
				{
					Logger.getLogger(Snapshot.class.getName()).log(Level.SEVERE, null, ex);
				}
					
				this.con.notifyEnd();
				id_file++;
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}