package server.commons.exceptions;

public enum MessageMap {
    SUCCESS("success"),
    ERROR("error"),
    EXECUTION_ERROR("Erro ao executar esta ação"),
    USER_NOT_FOUND("Usuário não encontrado!"),
    USER_EXISTS("Usuário já Existe!"),
    UPDATE_SUCCESS("Usuário Atualizado com Sucesso!"),
    DELETE_SUCCESS("Usuário Deletado com Sucesso!"),
    CREATE_SUCCESS("Usuário Criado com Sucesso!"),
    GET_SUCCESS("Usuário Recuperado com Sucesso!");

    private String message;

    MessageMap(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
