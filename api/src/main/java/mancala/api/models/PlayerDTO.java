package mancala.api.models;

import mancala.domain.Mancala;

public class PlayerDTO {
	public PlayerDTO(Mancala mancala, String name, boolean isFirstPlayer) {
		this.name = name;
		type = isFirstPlayer ? "player1" : "player2";
		hasTurn = mancala.isPlayersTurn(isFirstPlayer ? Mancala.Player.PLAYER_ONE : Mancala.Player.PLAYER_TWO);
		this.pits = new PitDTO[7];
		var firstHole = isFirstPlayer ? 0 : 7;
		for (int i = 0; i < 7; ++i) {
			this.pits[i] = new PitDTO(i + firstHole, mancala.getStonesForPit(i + firstHole));
		}
	}

	String name;

	public String getName() {
		return name;
	}

	String type;

	public String getType() {
		return type;
	}

	boolean hasTurn;

	public boolean getHasTurn() {
		return hasTurn;
	}

	PitDTO[] pits;

	public PitDTO[] getPits() {
		return pits;
	}
}