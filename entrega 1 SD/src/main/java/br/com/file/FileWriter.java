package br.com.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileWriter {

	/**
	 * Escreve o log das operacoes no arquivo
	 * @param path
	 * @param text
	 * @throws IOException
	 */
	public static void writeToFile( String path, String text ) throws IOException {
		List< String > list = new ArrayList< String >();
		list.add( text );
		Files.write( Paths.get( path ), list, StandardOpenOption.APPEND );
	}
	
	public static void moveFile( String source, String target ) {
		try {
			Files.move( Paths.get( source ), Paths.get( target ), StandardCopyOption.REPLACE_EXISTING );
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
	}
	
	public static void newFile( String source ) {
		try {
			Files.createFile( Paths.get( source ) );
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
	}

}
