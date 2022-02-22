package mancala.api.models;

import mancala.domain.Mancala;

public record PlayerDTO(String name, String type, boolean hasTurn,
                        PitDTO[] pits) {

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean getHasTurn() {
        return hasTurn;
    }

    public PitDTO[] getPits() {
        return pits;
    }

    public static PlayerDTO createPlayerDTO(Mancala mancala, String name, boolean isFirstPlayer) {
        String type = isFirstPlayer ? "player1" : "player2";
        boolean hasTurn = mancala.isPlayersTurn(isFirstPlayer ? Mancala.Player.PLAYER_ONE : Mancala.Player.PLAYER_TWO);

        PitDTO[] pits = new PitDTO[7];
        var firstHole = isFirstPlayer ? 0 : 7;
        for (int i = 0; i < 7; ++i) {
            pits[i] = new PitDTO(i + firstHole, mancala.getStonesForPit(i + firstHole));
        }

        return new PlayerDTO(name, type, hasTurn, pits);
    }
}