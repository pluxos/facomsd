package server.commons.exceptions;

public enum ErrorMap {
    BAD_REQUEST("Requisição inválida! Verifique se todos os dados foram passados corretamente"),
    INTERNAL_SERVER_ERROR("Erro inesperado! Tente novamente");

    private String message;

    ErrorMap(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
