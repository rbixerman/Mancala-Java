package mancala.api.models;

// This package is a collection of DTO's (data transfer objects).
// A DTO is a simple datastructure which models the
// data your web API sends back to the client. The Java
// objects will be converted to JSON objects.
import mancala.domain.Mancala;

public class MancalaDTO {
    public MancalaDTO(Mancala mancala, String namePlayer1, String namePlayer2) {
        players = new PlayerDTO[2];
        players[0] = new PlayerDTO(mancala, namePlayer1, true);
        players[1] = new PlayerDTO(mancala, namePlayer2, false);
        gameStatus = new GameStatusDTO(mancala, namePlayer1, namePlayer2);
    }

    PlayerDTO[] players;

    public PlayerDTO[] getPlayers() {
        return players;
    }

    GameStatusDTO gameStatus;

    public GameStatusDTO getGameStatus() {
        return gameStatus;
    }
}