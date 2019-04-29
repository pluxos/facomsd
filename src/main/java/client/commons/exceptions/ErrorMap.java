package client.commons.exceptions;

public enum ErrorMap {

	EMPTY_INPUT("Comando vazio!"), INVALID_COMMAND("Comando inválido!"), UNEXPECTED_ERROR("Erro inesperado! Tente novamente");
	
	private String message;
	
	ErrorMap(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}