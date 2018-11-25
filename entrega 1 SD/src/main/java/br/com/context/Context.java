package br.com.context;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.enums.Operation;

public class Context {

	private Map< BigInteger, Object > context;
	
	public Context() {
		this.context = new LinkedHashMap< BigInteger, Object >();
	}
	
	public void put( BigInteger key, Object object ) {
		context.put( key, object );
	}
	
	public void remove( BigInteger key ) {
		context.remove( key );
	}
	
	public void load( Path snapshotPath, Path logPath ) throws IOException {
		Files.lines( snapshotPath ).forEach( (line) -> load( line ) );
		Files.lines( logPath ).forEach( (line) -> load( line ) );
	}
	
	public void load( String line ) {
		List< String > list = Arrays.asList( line.split( ";" ) );
		
		if( Operation.INSERT.name().equals( list.get( 0 ) ) ) {
			context.put( new BigInteger( list.get( 1 ) ), list.get( 2 ) );
		} else if( Operation.UPDATE.name().equals( list.get( 0 ) ) ) {
			context.put( new BigInteger( list.get( 1 ) ), list.get( 2 ) );
		} else if( Operation.DELETE.name().equals( list.get( 0 ) ) ) {
			context.remove( new BigInteger( list.get( 1 ) ) );
		}
	}
	
	public String stringfy() {
		String toString = "";
		for( Map.Entry< BigInteger, Object > entry : context.entrySet() ) {
			toString = toString.concat( "(" + entry.getKey().toString() + "," + entry.getValue().toString() + ")" );
		}
		return toString;
	}
	
	public String get( BigInteger key ) {
		if( context.get( key ) != null ) {
			return "(" + key + "," + context.get( key ) + ")";
		} else {
			return "Chave nao encontrada no contexto.";
		}
	}
	
	public Map< BigInteger, Object > get() {
		return this.context;
	}
	
}
