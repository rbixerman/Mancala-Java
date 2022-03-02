package mancala.domain;

import mancala.domain.exceptions.GameIsOverException;
import mancala.domain.exceptions.NotAValidMoveException;
import mancala.domain.exceptions.NotYourBowlException;

public class Bowl {

    private static final int BOARD_SIZE = 14;
    private static final int STARTING_STONES = 4;
    private final Bowl neighbour;
    private final Player owner;
    private int numberOfStones;

    public Bowl() {
        this(STARTING_STONES, new Player());
    }

    protected Bowl(int numberOfStones, Player player1) {
        this.numberOfStones = numberOfStones;
        this.owner = player1;
        this.neighbour = createNeighbour(this, BOARD_SIZE - 2);
    }

    protected Bowl(int numStone, Bowl first, int neighboursLeft) {
        this.numberOfStones = numStone;
        this.owner = (neighboursLeft >= 7) ? first.getOwner() : first.getOwner().getOpponent();

        this.neighbour = (neighboursLeft == 0) ? first : createNeighbour(first, neighboursLeft - 1);
    }

    public Bowl(Player player1) {
        this(STARTING_STONES, player1);
    }

    private static Bowl createNeighbour(Bowl first, int neighboursLeft) {
        if (neighboursLeft % (BOARD_SIZE / 2) == 0) {
            return new Kalaha(first, neighboursLeft);
        } else {
            return new Bowl(STARTING_STONES, first, neighboursLeft);
        }
    }

    public int getNumberOfStones() {
        return this.numberOfStones;
    }

    protected void addStones(int amount) {
        this.numberOfStones += amount;
    }

    public void doMove() throws NotAValidMoveException, GameIsOverException, NotYourBowlException {
        if (isGameOver()) {
            throw new GameIsOverException();
        }

        if (!getOwner().isActive()) {
            throw new NotYourBowlException();
        }
        if (numberOfStones == 0) {
            throw new NotAValidMoveException();
        }

        final int stonesToPass = this.numberOfStones;
        this.numberOfStones = 0;

        getNeighbour().passStones(this, stonesToPass);
        getOwner().switchTurns();

        if (isGameOver()) {
            Bowl otherSideStart = getKalaha().getNeighbour();
            Bowl thisSideStart = otherSideStart.getKalaha().getNeighbour();

            otherSideStart.passAllToNext();
            thisSideStart.passAllToNext();
        }
    }

    protected void passAllToNext() {
        int stonesToPass = getNumberOfStones();
        numberOfStones = 0;
        getNeighbour().addStones(stonesToPass);
        getNeighbour().passAllToNext();

    }

    protected void passStones(Bowl from, int stonesToPass) {
        if (stonesToPass >= 1) {
            this.numberOfStones++;
        }

        if (shouldCapture(from, stonesToPass)) {
            int amountCaptured = getOpposite().captureStones() + captureStones();
            passCapturedStones(this, amountCaptured);
        } else if (stonesToPass >= 1) {
            getNeighbour().passStones(from, stonesToPass - 1);
        }
    }

    private boolean shouldCapture(Bowl from, int stonesToPass) {
        return this.numberOfStones == 1
                && stonesToPass == 1
                && isSameTeam(from)
                && getOpposite().getNumberOfStones() > 0;
    }

    private boolean isSameTeam(Bowl other) {
        return this.getKalaha().equals(other.getKalaha());
    }

    protected void passCapturedStones(Bowl captor, int amountCaptured) {
        getNeighbour().passCapturedStones(captor, amountCaptured);
    }

    private int captureStones() {
        int amountStolen = this.numberOfStones;
        this.numberOfStones = 0;

        return amountStolen;
    }

    public Bowl getNeighbour() {
        return this.neighbour;
    }

    public Bowl getNeighbour(int i) {
        if (i == 0) {
            return this;
        }
        return this.getNeighbour().getNeighbour(i - 1);
    }

    public Bowl getOpposite() {
        return getNeighbour().getOpposite().getNeighbour();
    }

    public Bowl getKalaha() {
        return getNeighbour().getKalaha();
    }

    public boolean isEmpty() {
        return this.numberOfStones == 0;
    }

    public int getScore(Player player) {
        if (getOwner() == player) {
            Bowl startOfSide = this.getKalaha().getNeighbour().getKalaha().getNeighbour();

            return getNumberOfStones() + getNeighbour().requestScore();
        } else {
            return getNeighbour().getScore(player);
        }
    }

    protected int requestScore() {
        return getNumberOfStones() + getNeighbour().requestScore();
    }


    public boolean isSideEmpty() {
        Bowl startOfSide = this.getKalaha().getNeighbour().getKalaha().getNeighbour();

        return startOfSide.checkSideEmpty();
    }

    protected boolean checkSideEmpty() {
        return this.isEmpty() && getNeighbour().checkSideEmpty();
    }

    public boolean isGameOver() {
        return this.isSideEmpty() || this.getKalaha().getNeighbour().isSideEmpty();
    }

    public Player getOwner() {
        return this.owner;
    }

    public Player getWinner() {
        if (!isGameOver()) {
            return null;
        }

        Player opponent = getOwner().getOpponent();

        int myScore = getScore(getOwner());
        int oppScore = getScore(opponent);

        if (myScore > oppScore) {
            return getOwner();
        }

        if (oppScore > myScore) {
            return opponent;
        }

        return null;
    }
}
