package mancala.api.models;

// This package is a collection of DTO's (data transfer objects).
// A DTO is a simple datastructure which models the
// data your web API sends back to the client. The Java
// objects will be converted to JSON objects.
import mancala.domain.Mancala;

public record MancalaDTO(PlayerDTO[] players, GameStatusDTO gameStatus) {

    public PlayerDTO[] getPlayers() {
        return players;
    }

    public GameStatusDTO getGameStatus() {
        return gameStatus;
    }

    public static MancalaDTO createMancalaDTO(Mancala mancala, String namePlayer1, String namePlayer2) {
        PlayerDTO[] players = new PlayerDTO[2];
        players[0] = PlayerDTO.createPlayerDTO(mancala, namePlayer1, true);
        players[1] = PlayerDTO.createPlayerDTO(mancala, namePlayer2, false);
        GameStatusDTO gameStatus = new GameStatusDTO(mancala, namePlayer1, namePlayer2);

        return new MancalaDTO(players, gameStatus);
    }
}
