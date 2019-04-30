package client.commons.exceptions;

public enum ErrorMap {

	EMPTY_INPUT("Comando vazio!"),
	INVALID_COMMAND("Comando inválido!"),
	UNEXPECTED_ERROR("Erro inesperado! Tente novamente"),
	INVALID_ID("Id deve conter apenas caracteres numéricos"),
	INVALID_PASSWORD("Senha deve conter apenas letras e/ou números"),
	INVALID_EMAIL("Email inválido!"),
	INVALID_NAME("Nome não pode conter caracters numéricos"),
	INVALID_PARAMS_LENGTH("Quantidade de parâmetros errada!"),
	UNDEFINED_METHOD("Método digitado não reconhecido! Deve ser get, create, update ou delete");
	
	private String message;
	
	ErrorMap(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}