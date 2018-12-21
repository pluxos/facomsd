package br.com.enums;

public enum Operation {

	INSERT( "INSERT" ), 
	DELETE( "DELETE" ),
	UPDATE( "UPDATE" ),
	RETURN( "RETURN" ),
	SUBSCRIBE( "SUBSCRIBE" ),;
	
	private String value;
	
	Operation( String value ) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
	
}
