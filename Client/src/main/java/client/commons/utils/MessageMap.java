package client.commons.utils;

public enum MessageMap {
    SUCCESS("success"),
    ERROR("error");

    private String message;

    MessageMap(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }
}
