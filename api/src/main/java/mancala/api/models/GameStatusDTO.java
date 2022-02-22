package mancala.api.models;

import mancala.domain.Mancala;

public class GameStatusDTO {

    private final boolean endOfGame;
    private final String winner;

    public GameStatusDTO(Mancala mancala, String namePlayer1, String namePlayer2) {

        this.endOfGame = mancala.isEndOfGame();

        Mancala.Winner winner = mancala.getWinner();

        this.winner = switch(winner) {
            case NO_PLAYERS -> "no player has won yet";
            case PLAYER_ONE -> namePlayer1;
            case PLAYER_TWO -> namePlayer2;
            case BOTH_PLAYERS -> namePlayer1 + "and" + namePlayer2;
        };
    }

    public boolean getEndOfGame() {
        return endOfGame;
    }

    public String getWinner() {
        return winner;
    }
}