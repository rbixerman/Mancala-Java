package mancala.domain;

import mancala.domain.exceptions.GameIsOverException;
import mancala.domain.exceptions.NotAValidMoveException;

public class Kalaha extends Bowl {

    private static final int KALAHA_STARTING_STONES = 0;

    protected Kalaha(Bowl first, int neighboursLeft) {
        super(KALAHA_STARTING_STONES, first, neighboursLeft);
    }

    @Override
    public void doMove() throws NotAValidMoveException, GameIsOverException {
        if (isGameOver()) {
            throw new GameIsOverException();
        }
        throw new NotAValidMoveException();
    }

    @Override
    public Bowl getOpposite() {
        return this;
    }

    @Override
    public Bowl getKalaha() {
        return this;
    }

    @Override
    protected void passStones(Bowl from, int stonesToPass) {
        if (from.getKalaha() != this) {
            getNeighbour().passStones(from, stonesToPass);
        } else {
            super.passStones(from, stonesToPass);

            if (stonesToPass == 1) {
                getOwner().switchTurns();
            }
        }
    }

    @Override
    protected void passAllToNext() {
        // Nothing to do here, stop the passing process.
    }

    @Override
    protected void passCapturedStones(Bowl captor, int amountCaptured) {
        if (captor.getKalaha() != this) {
            getNeighbour().passCapturedStones(captor, amountCaptured);
        } else {
            this.addStones(amountCaptured);
        }
    }

    protected boolean checkSideEmpty() {
        return true;
    }

    protected int requestScore() {
        return getNumberOfStones();
    }
}
