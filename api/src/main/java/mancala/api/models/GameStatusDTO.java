package mancala.api.models;

import mancala.domain.Mancala;

public class GameStatusDTO {

    boolean endOfGame;

    public boolean getEndOfGame() {
        return endOfGame;
    }

    String winner;

    public String getWinner() {
        return winner;
    }

    public GameStatusDTO(Mancala mancala, String namePlayer1, String namePlayer2) {

        this.endOfGame = mancala.isEndOfGame();

        Mancala.Winner winner = mancala.getWinner();
        if (winner == Mancala.Winner.NO_PLAYERS) {
            this.winner = null;
        } else if (winner == Mancala.Winner.PLAYER_ONE) {
            this.winner = namePlayer1;
        } else if (winner == Mancala.Winner.PLAYER_TWO) {
            this.winner = namePlayer2;
        } else {
            this.winner = namePlayer1 + "and" + namePlayer2;
        }
    }
}