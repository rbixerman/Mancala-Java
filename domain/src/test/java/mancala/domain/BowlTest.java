package mancala.domain;

import mancala.domain.exceptions.GameIsOverException;
import mancala.domain.exceptions.NotAValidMoveException;
import mancala.domain.exceptions.NotYourBowlException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class BowlTest {

    /* Make sure the board has the correct geometry/topology and initial state
     */
    @Test
    public void a_normal_bowl_starts_with_4_stones() {
        Bowl bowl = new Bowl();

        assertEquals(4, bowl.getNumberOfStones());
    }

    @Test
    public void a_bowl_always_has_a_neighbour() {
        Bowl bowl = new Bowl();

        assertNotNull(bowl.getNeighbour());
    }

    @Test
    public void a_bowls_its_own_14th_neighbour() {
        Bowl bowl = new Bowl();

        assertEquals(bowl, bowl.getNeighbour(14));
    }

    @Test
    public void a_bowls_2nd_neighbour_is_the_neighbour_of_its_neighbour() {
        Bowl bowl = new Bowl();

        assertEquals(bowl.getNeighbour().getNeighbour(), bowl.getNeighbour(2));
    }

    @Test
    public void all_14_bowls_are_unique() {
        Bowl bowl = new Bowl();
        ArrayList<Bowl> bowls = new ArrayList<>();

        for (int i = 1; i <= 14; i++) {
            bowls.add(bowl.getNeighbour(i));
        }

        assertEquals(bowls.size(), new HashSet<>(bowls).size());
    }

    @Test
    public void the_first_bowls_6th_neighbour_is_a_kalaha() {
        Bowl bowl = new Bowl();

        assertTrue(bowl.getNeighbour(6) instanceof Kalaha);
    }

    @Test
    public void a_bowl_knows_its_opposite() {
        Bowl bowl = new Bowl();

        assertEquals(bowl.getNeighbour(12), bowl.getOpposite());
        assertEquals(bowl.getNeighbour(11), bowl.getNeighbour(1).getOpposite());
        assertEquals(bowl.getNeighbour(10), bowl.getNeighbour(2).getOpposite());
        assertEquals(bowl.getNeighbour(9), bowl.getNeighbour(3).getOpposite());
    }

    @Test
    public void a_bowl_knows_its_kalaha() {
        Bowl bowl = new Bowl();

        assertEquals(bowl.getNeighbour(6), bowl.getKalaha());
    }

    @Test
    public void a_kalaha_starts_with_0_stones() {
        Bowl kalaha = new Bowl().getKalaha();
        assertEquals(0, kalaha.getNumberOfStones());
    }

    @Test
    public void the_bowls_have_correct_owners() {
        Player player1 = new Player();
        Player player2 = player1.getOpponent();

        Bowl bowl = new Bowl(player1);

        assertEquals(player1, bowl.getNeighbour(0).getOwner());
        assertEquals(player1, bowl.getNeighbour(1).getOwner());
        assertEquals(player1, bowl.getNeighbour(2).getOwner());
        assertEquals(player1, bowl.getNeighbour(3).getOwner());
        assertEquals(player1, bowl.getNeighbour(4).getOwner());
        assertEquals(player1, bowl.getNeighbour(5).getOwner());
        assertEquals(player1, bowl.getNeighbour(6).getOwner());
        assertEquals(player2, bowl.getNeighbour(7).getOwner());
        assertEquals(player2, bowl.getNeighbour(8).getOwner());
        assertEquals(player2, bowl.getNeighbour(9).getOwner());
        assertEquals(player2, bowl.getNeighbour(10).getOwner());
        assertEquals(player2, bowl.getNeighbour(11).getOwner());
        assertEquals(player2, bowl.getNeighbour(12).getOwner());
        assertEquals(player2, bowl.getNeighbour(13).getOwner());
    }

    /*
     * The tests in this section are now guaranteed to have a coherent board in the initial state, making it easy
     * to test some default moves.
     */
    @Test
    public void an_empty_bowl_is_not_a_valid_move() {
        Bowl bowl = new Bowl(0, new Player());
        assertThrows(NotAValidMoveException.class, bowl::doMove);
    }

    @Test
    public void a_kalaha_is_not_a_valid_move() throws NotAValidMoveException {
        Bowl kalaha = new Bowl().getKalaha();
        assertThrows(NotAValidMoveException.class, kalaha::doMove);
    }

    @Test
    public void only_the_active_players_bowls_can_be_moved() throws GameIsOverException, NotYourBowlException, NotAValidMoveException {
        Bowl bowl = new Bowl();
        assertThrows(NotYourBowlException.class, bowl.getNeighbour(7)::doMove);

        bowl.doMove();
        assertThrows(NotYourBowlException.class, bowl::doMove);
    }

    @Test
    public void a_move_empties_the_corresponding_bowl() throws NotAValidMoveException, GameIsOverException, NotYourBowlException {
        Bowl bowl = new Bowl();
        bowl.doMove();

        assertEquals(0, bowl.getNumberOfStones());
    }

    @Test
    public void a_move_that_doesnt_end_in_a_kalaha_switches_player_turns() throws GameIsOverException, NotYourBowlException, NotAValidMoveException {
        Player player = new Player();

        Bowl bowl = new Bowl(player);
        bowl.doMove();

        assertFalse(player.isActive());
    }

    @Test
    public void a_move_that_ends_in_your_kalaha_doesnt_switch_player_turns() throws GameIsOverException, NotYourBowlException, NotAValidMoveException {
        Player player = new Player();
        Bowl bowl = new Bowl(player);
        bowl.getNeighbour(2).doMove();

        assertTrue(player.isActive());
    }

    @Test
    public void a_move_gives_a_stone_to_its_neighbour() throws NotAValidMoveException, GameIsOverException, NotYourBowlException {
        Bowl bowl = new Bowl();
        bowl.doMove();

        assertEquals(5, bowl.getNeighbour().getNumberOfStones());
    }

    @Test
    public void a_move_passes_all_stones() throws NotAValidMoveException, GameIsOverException, NotYourBowlException {
        Bowl bowl = new Bowl();
        bowl.doMove();


        for (int i = 1; i <= 4; i++) {
            assertEquals(5, bowl.getNeighbour(i).getNumberOfStones());
        }
    }

    private int[] getRelativeBoardStateArray(Bowl bowl) {
        int[] board = new int[14];

        for (int i = 0; i < 14; i++) {
            board[i] = bowl.getNeighbour(i).getNumberOfStones();
        }

        return board;
    }

    @Test
    public void a_move_doesnt_pass_more_stones_than_available() throws NotAValidMoveException, GameIsOverException, NotYourBowlException {
        Bowl bowl = new Bowl();
        bowl.doMove();

        int[] expected = new int[]{0, 5, 5, 5, 5, 4, 0, 4, 4, 4, 4, 4, 4, 0};
        assertArrayEquals(expected, getRelativeBoardStateArray(bowl));
    }

    @Test
    public void a_kalaha_takes_a_stone_when_offered_by_an_allied_bowl() throws NotAValidMoveException, GameIsOverException, NotYourBowlException {
        Bowl bowl = new Bowl();
        bowl.getNeighbour(3).doMove();

        int[] expected = new int[]{4, 4, 4, 0, 5, 5, 1, 5, 4, 4, 4, 4, 4, 0};
        assertArrayEquals(expected, getRelativeBoardStateArray(bowl));
    }

    @Test
    public void a_kalaha_does_not_take_a_stone_when_offered_by_an_enemy_bowl() throws NotAValidMoveException, GameIsOverException, NotYourBowlException {
        Bowl bowl = new Bowl(14, new Player());
        bowl.doMove();

        int[] expected = new int[]{1, 6, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 0};
        assertArrayEquals(expected, getRelativeBoardStateArray(bowl));
    }

    @Test
    public void an_empty_allied_bowl_captures_stones_when_passed_the_final_stone_of_a_move_and_opposing_bowl_is_not_empty() throws NotAValidMoveException, GameIsOverException, NotYourBowlException {
        Bowl bowl = new Bowl(13, new Player());
        bowl.doMove();

        int[] expected = new int[]{0, 5, 5, 5, 5, 5, 7, 5, 5, 5, 5, 5, 0, 0};
        assertArrayEquals(expected, getRelativeBoardStateArray(bowl));
    }

    @Test
    public void an_empty_allied_bowl_does_not_capture_stones_when_passed_the_final_stone_of_a_move_and_opposing_bowl_is_empty() throws GameIsOverException, NotYourBowlException, NotAValidMoveException {
        Bowl bowl = new Bowl();

        bowl.getNeighbour(4).doMove();
        bowl.getNeighbour(8).doMove();
        bowl.getNeighbour(7).doMove();
        bowl.getNeighbour(5).doMove();
        bowl.getNeighbour(8).doMove();
        bowl.doMove();

        int[] expected = new int[]{0, 5, 5, 5, 1, 0, 2, 1, 0, 8, 8, 6, 6, 1};
        assertArrayEquals(expected, getRelativeBoardStateArray(bowl));
    }

    @Test
    public void an_empty_enemy_bowl_does_not_capture_stones_when_passed_the_final_stone_of_a_move() throws NotAValidMoveException, GameIsOverException, NotYourBowlException {
        Bowl bowl = new Bowl();
        bowl.doMove();

        //now bowl 0 is empty, do enemy move that ends in bowl 0
        bowl.getNeighbour(10).doMove();

        // no capturing should happen
        int[] expected = new int[]{1, 5, 5, 5, 5, 4, 0, 4, 4, 4, 0, 5, 5, 1};

        assertArrayEquals(expected, getRelativeBoardStateArray(bowl));
    }

    @Test
    public void a_sequence_of_moves_should_behave_as_expected() throws NotAValidMoveException, GameIsOverException, NotYourBowlException {
        Bowl bowl = new Bowl();
        bowl.getNeighbour(2).doMove();
        bowl.getNeighbour(3).doMove();
        bowl.getNeighbour(9).doMove();
        bowl.getNeighbour(12).doMove();
        bowl.getNeighbour(1).doMove();
        bowl.getNeighbour(4).doMove();
        bowl.getNeighbour(9).doMove();
        bowl.getNeighbour(2).doMove();
        bowl.getNeighbour(7).doMove();
        bowl.getNeighbour(12).doMove();
        bowl.getNeighbour(10).doMove();
        bowl.getNeighbour(3).doMove();
        bowl.getNeighbour(9).doMove();
        bowl.getNeighbour(5).doMove();
        bowl.getNeighbour(11).doMove();

        int[] expected = new int[]{8, 3, 1, 1, 3, 1, 13, 3, 2, 1, 1, 0, 3, 8};
        assertArrayEquals(expected, getRelativeBoardStateArray(bowl));
    }

    @Test
    public void is_empty_should_be_true_when_bowls_side_is_empty() throws NotAValidMoveException, GameIsOverException, NotYourBowlException {
        Bowl bowl = new Bowl();

        bowl.doMove();
        assertFalse(bowl.isSideEmpty());

        bowl.getNeighbour(7).doMove();
        assertFalse(bowl.isSideEmpty());

        bowl.getNeighbour(1).doMove();
        assertFalse(bowl.isSideEmpty());

        bowl.getNeighbour(2).doMove();
        assertFalse(bowl.isSideEmpty());

        bowl.getNeighbour(7).doMove();
        assertFalse(bowl.isSideEmpty());

        bowl.getNeighbour(3).doMove();
        assertFalse(bowl.isSideEmpty());

        bowl.getNeighbour(7).doMove();
        assertFalse(bowl.isSideEmpty());

        bowl.getNeighbour(4).doMove();
        assertFalse(bowl.isSideEmpty());

        bowl.getNeighbour(7).doMove();
        assertFalse(bowl.isSideEmpty());

        bowl.getNeighbour(5).doMove();
        assertTrue(bowl.isSideEmpty()); // At this point the game should be over according to rules
    }

    @Test
    public void test_is_game_over() throws NotAValidMoveException, GameIsOverException, NotYourBowlException {
        Bowl bowl = new Bowl();

        bowl.doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(7).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(1).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(2).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(7).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(3).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(7).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(4).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(7).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(5).doMove();
        assertTrue(bowl.getNeighbour(7).isGameOver());
    }

    @Test
    public void when_game_is_over_no_move_can_be_made() throws NotAValidMoveException, GameIsOverException, NotYourBowlException {
        Bowl bowl = new Bowl();

        bowl.doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(7).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(1).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(2).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(7).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(3).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(7).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(4).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(7).doMove();
        assertFalse(bowl.getNeighbour(7).isGameOver());

        bowl.getNeighbour(5).doMove();
        assertTrue(bowl.getNeighbour(7).isGameOver());

        for (int i = 0; i <= 13; i++) {
            assertThrows(GameIsOverException.class, bowl.getNeighbour(i)::doMove);
        }
    }

    @Test
    public void when_game_is_not_over_there_is_no_winner() {
        Bowl bowl = new Bowl();

        assertNull(bowl.getWinner());
    }

    @Test
    public void when_game_is_over_and_player_two_has_higher_score_he_is_the_winner() throws GameIsOverException, NotYourBowlException, NotAValidMoveException {
        Player player1 = new Player();
        Bowl bowl = new Bowl(player1);
        Player player2 = player1.getOpponent();

        bowl.doMove();
        bowl.getNeighbour(7).doMove();
        bowl.getNeighbour(1).doMove();
        bowl.getNeighbour(2).doMove();
        bowl.getNeighbour(7).doMove();
        bowl.getNeighbour(3).doMove();
        bowl.getNeighbour(7).doMove();
        bowl.getNeighbour(4).doMove();
        bowl.getNeighbour(7).doMove();
        bowl.getNeighbour(5).doMove();

        assertEquals(player2, bowl.getWinner());
    }

    @Test
    public void when_the_game_is_over_the_scores_are_correct() throws GameIsOverException, NotYourBowlException, NotAValidMoveException {
        Player player1 = new Player();
        Bowl bowl = new Bowl(player1);
        Player player2 = player1.getOpponent();

        bowl.getNeighbour(3).doMove();
        bowl.getNeighbour(7).doMove();

        bowl.getNeighbour(0).doMove();
        bowl.getNeighbour(8).doMove();

        bowl.getNeighbour(9).doMove();
        bowl.getNeighbour(0).doMove();

        bowl.getNeighbour(10).doMove();
        bowl.getNeighbour(0).doMove();

        bowl.getNeighbour(12).doMove();
        bowl.getNeighbour(0).doMove();

        bowl.getNeighbour(11).doMove();
        bowl.getNeighbour(0).doMove();

        bowl.getNeighbour(12).doMove();

        assertEquals(35, bowl.getScore(player1));
        assertEquals(13, bowl.getScore(player2));
    }

    @Test
    public void when_game_is_over_and_player_one_has_higher_score_he_is_the_winner() throws GameIsOverException, NotYourBowlException, NotAValidMoveException {
        Player player1 = new Player();
        Bowl bowl = new Bowl(player1);
        Player player2 = player1.getOpponent();

        bowl.getNeighbour(3).doMove();
        bowl.getNeighbour(7).doMove();

        bowl.getNeighbour(0).doMove();
        bowl.getNeighbour(8).doMove();

        bowl.getNeighbour(9).doMove();
        bowl.getNeighbour(0).doMove();

        bowl.getNeighbour(10).doMove();
        bowl.getNeighbour(0).doMove();

        bowl.getNeighbour(12).doMove();
        bowl.getNeighbour(0).doMove();

        bowl.getNeighbour(11).doMove();
        bowl.getNeighbour(0).doMove();

        bowl.getNeighbour(12).doMove();

        assertEquals(player1, bowl.getWinner());
    }

    @Test
    public void when_game_is_over_all_stones_should_be_moved_to_the_correct_kalaha() throws GameIsOverException, NotAValidMoveException, NotYourBowlException {

        Player player1 = new Player();
        Bowl bowl = new Bowl(player1);
        Player player2 = player1.getOpponent();

        bowl.getNeighbour(3).doMove();
        bowl.getNeighbour(7).doMove();

        bowl.getNeighbour(0).doMove();
        bowl.getNeighbour(8).doMove();

        bowl.getNeighbour(9).doMove();
        bowl.getNeighbour(0).doMove();

        bowl.getNeighbour(10).doMove();
        bowl.getNeighbour(0).doMove();

        bowl.getNeighbour(12).doMove();
        bowl.getNeighbour(0).doMove();

        bowl.getNeighbour(11).doMove();
        bowl.getNeighbour(0).doMove();

        bowl.getNeighbour(12).doMove();

        assertEquals(35, bowl.getScore(player1));
        assertEquals(13, bowl.getScore(player2));

        int[] expected = new int[]{0,0,0,0,0,0,35,0,0,0,0,0,0,13};
        assertArrayEquals(expected, getRelativeBoardStateArray(bowl));
    }
}