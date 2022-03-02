package mancala.domain;

import mancala.domain.exceptions.MancalaException;

public interface Mancala {

    /**
     * Method indicating if the first player has the next turn or not.
     * If player 1 is not in turn, then player 2 is in turn.
     *
     * @param player The player which you want to know the turn for.
     * @return True if the first player has the next turn, false if it's the turn of
     * the other player.
     */
    boolean isPlayersTurn(Mancala.Player player);

    /**
     * Method for playing the specified recess. Index is as specified below:
     * <p>
     * 12 11 10 9 8 7
     * 13 6
     * 0 1 2 3 4 5
     *
     * @param index Index of the recess to be played.
     */
    void playPit(int index) throws MancalaException;

    /**
     * Method for returning the amount of stones in de specified pit. Index is as
     * specified below:
     * <p>
     * 12 11 10 9 8 7
     * 13 6
     * 0 1 2 3 4 5
     *
     * @param index Index of the pit.
     * @return Amount of stone.
     */
    int getStonesForPit(int index);

    /**
     * Method for retrieving whether the game has ended or not.
     *
     * @return True is the game has ended otherwise False.
     */
    boolean isEndOfGame();

    /**
     * Method for retrieving the player that has won the game.
     *
     * @return Integer value representing which player(s) (if any) won the game.
     */
    Winner getWinner();

    enum Player {
        PLAYER_ONE, PLAYER_TWO
    }

    enum Winner {
        NO_PLAYERS, PLAYER_ONE, PLAYER_TWO, BOTH_PLAYERS
    }

}
