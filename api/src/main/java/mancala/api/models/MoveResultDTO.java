package mancala.api.models;

public record MoveResultDTO(boolean success, MancalaDTO newGameState,
                            String errorMessage) {

    public boolean isSuccess() {
        return success;
    }

    public MancalaDTO getNewGameState() {
        return newGameState;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}