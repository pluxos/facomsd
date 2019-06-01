package client.commons.exceptions;

public class InvalidCommandException extends DomainException {

	private static final long serialVersionUID = 1L;

	public InvalidCommandException(ErrorMap error) {
		super(error);
	}
}