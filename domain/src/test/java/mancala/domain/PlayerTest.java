package mancala.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void a_player_has_an_opponent() {
        Player player = new Player();

        assertNotNull(player.getOpponent());
    }

    @Test
    public void getting_opponent_is_an_involution() {
        Player player = new Player();

        assertEquals(player, player.getOpponent().getOpponent());
    }

    @Test
    public void a_player_and_its_opponent_are_not_both_active_or_inactive() {
        Player player = new Player();
        Player opponent = player.getOpponent();

        assertNotEquals(player.isActive(), opponent.isActive());
    }

    @Test
    public void a_player_switching_turns_updates_both_players() {
        Player player = new Player();
        player.switchTurns();

        assertFalse(player.isActive());
        assertTrue(player.getOpponent().isActive());
    }
}
