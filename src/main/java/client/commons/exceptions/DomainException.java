package client.commons.exceptions;

public class DomainException extends Exception {

	private static final long serialVersionUID = 1L;

	private ErrorMap error;
	
	public DomainException(ErrorMap error) {
		this.error = error;
	}
	
	public String getErrorMessage() {
		return error.getMessage();
	}
}