package mancala.domain;

import java.util.List;

public class Player {

    private final Player opponent;
    private boolean isActive;

    public Player() {
        this.isActive = true;
        this.opponent = new Player(this);
    }

    private Player(Player opponent) {
        this.isActive = false;
        this.opponent = opponent;
    }


    public Player getOpponent() {
        return this.opponent;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void switchTurns() {
        this.isActive = !this.isActive;
        getOpponent().switchTurn();
    }

    private void switchTurn() {
        this.isActive = !this.isActive;
    }
}
