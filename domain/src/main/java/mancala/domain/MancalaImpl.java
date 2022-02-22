package mancala.domain;

import mancala.domain.exceptions.MancalaException;

public class MancalaImpl implements Mancala {

    private final mancala.domain.Player playerOne;
    private final mancala.domain.Player playerTwo;

    private final Bowl game;

    public MancalaImpl() {
        this.game = new Bowl();
        this.playerOne = game.getOwner();
        this.playerTwo = game.getOwner().getOpponent();
    }

    @Override
    public boolean isPlayersTurn(Mancala.Player player) {
        return switch(player) {
            case PLAYER_ONE -> playerOne.isActive();
            case PLAYER_TWO -> playerTwo.isActive();
        };
    }

    @Override
    public void playPit(int index) throws MancalaException {
        game.getNeighbour(index).doMove();
    }

    @Override
    public int getStonesForPit(int index) {
        return game.getNeighbour(index).getNumberOfStones();
    }

    @Override
    public boolean isEndOfGame() {
        return game.isGameOver();
    }

    @Override
    public Mancala.Winner getWinner() {
        if (!isEndOfGame()) {
            return Winner.NO_PLAYERS;
        }

        mancala.domain.Player winner = game.getWinner();

        if (winner.equals(playerOne)) {
            return Winner.PLAYER_ONE;
        }

        if (winner.equals(playerTwo)) {
            return Winner.PLAYER_TWO;
        }

        return Winner.BOTH_PLAYERS;
    }
























}