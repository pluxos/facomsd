package br.com.thread;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

import br.com.context.Context;
import br.com.file.FileWriter;

public class SnapshotThread implements Runnable {

	private final String SNAPSHOT_FILE = "src/main/resources/snapshot/snapshot#DATE#.txt";
	private final String SNAPSHOT_BACKUP_FILE = "src/main/resources/snapshot/old/snapshot#DATE#.txt";

	private final String LOG_PATH = "src/main/resources/log/log#DATE#.txt";
	private final String LOG_BACKUP_PATH = "src/main/resources/log/old/log#DATE#.txt";


	private Boolean semaphore;
	private Context context;
	
	public SnapshotThread( Context context, Boolean semaphore ) {
		this.context = context;
		this.semaphore = semaphore;
	}
	
	@Override
	public void run() {
		
		while( true ) {
			
			try {
				
				Thread.sleep( 10000 );
				
				semaphore = true;
				
				Date now = new Date();
				Map< BigInteger, Object > contextMap = context.get();
				
				FileWriter.moveFile( 
					SNAPSHOT_FILE.replace( "#DATE#", "" ), 
					SNAPSHOT_BACKUP_FILE.replace( "#DATE#", String.valueOf( now.getTime() ) ) );
				
				FileWriter.moveFile( 
					LOG_PATH.replace( "#DATE#", "" ), 
					LOG_BACKUP_PATH.replace( "#DATE#", String.valueOf( now.getTime() ) ) );
				
				FileWriter.newFile( SNAPSHOT_FILE.replace( "#DATE#", "" ) );
				FileWriter.newFile( LOG_PATH.replace( "#DATE#", "" ) );
				
				for( Map.Entry< BigInteger, Object > entry : contextMap.entrySet() ) {
					String command = "INSERT;" + entry.getKey().toString() + ";" + entry.getValue().toString();
					FileWriter.writeToFile( SNAPSHOT_FILE.replace( "#DATE#", "" ), command );
				}
				
				semaphore = false;
			
			} catch( Exception donothing ) {
				
			}
			
		}
		
	}

}
