package mancala.domain.exceptions;

public class GameIsOverException extends MancalaException {
    public GameIsOverException() {
        super("The game is already over!");
    }
}
