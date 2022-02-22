package mancala.domain.exceptions;

public class NotAValidMoveException extends MancalaException {
    public NotAValidMoveException() {
        super("Not a valid move!");
    }
}
