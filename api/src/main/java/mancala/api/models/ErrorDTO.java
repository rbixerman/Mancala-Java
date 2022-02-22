package mancala.api.models;

public record ErrorDTO(String message) {

    public String getMessage() {
        return message;
    }
}
