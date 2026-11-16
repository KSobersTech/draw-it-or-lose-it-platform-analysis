package tech.ksobers.drawit.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameServiceTest {

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService();
    }

    @Test
    void createsNestedGameTeamAndPlayerGraph() {
        Game game = gameService.createGame("  Quick Draw  ");
        Team team = gameService.addTeam(game.getId(), "Moonlight");
        Player player = gameService.addPlayer(game.getId(), team.getId(), "Keishmar");

        assertEquals("Quick Draw", game.getName());
        assertEquals(team, game.findTeam(" moonLIGHT ").orElseThrow());
        assertEquals(player, team.findPlayer("KEISHMAR").orElseThrow());
        assertEquals(1, game.getTeams().size());
        assertEquals(1, team.getPlayers().size());
    }

    @Test
    void rejectsDuplicateNamesWithoutConsumingAnotherIdentifier() {
        Game first = gameService.createGame("Quick Draw");

        assertThrows(DuplicateNameException.class, () -> gameService.createGame(" quick DRAW "));

        Game second = gameService.createGame("Sketch Relay");
        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
    }

    @Test
    void scopesTeamAndPlayerNamesToTheirParents() {
        Game firstGame = gameService.createGame("First");
        Game secondGame = gameService.createGame("Second");
        Team firstTeam = gameService.addTeam(firstGame.getId(), "Blue");
        Team secondTeam = gameService.addTeam(secondGame.getId(), "Blue");

        Player firstPlayer = gameService.addPlayer(firstGame.getId(), firstTeam.getId(), "Alex");
        Player secondPlayer = gameService.addPlayer(secondGame.getId(), secondTeam.getId(), "Alex");

        assertNotEquals(firstTeam.getId(), secondTeam.getId());
        assertNotEquals(firstPlayer.getId(), secondPlayer.getId());
        assertThrows(DuplicateNameException.class,
                () -> gameService.addPlayer(firstGame.getId(), firstTeam.getId(), " alex "));
    }

    @Test
    void validatesNamesAndMissingParents() {
        Game game = gameService.createGame("Quick Draw");

        assertThrows(IllegalArgumentException.class, () -> gameService.createGame("   "));
        assertThrows(EntityNotFoundException.class, () -> gameService.addTeam(999, "Blue"));
        assertThrows(EntityNotFoundException.class, () -> gameService.addPlayer(game.getId(), 999, "Alex"));
    }

    @Test
    void returnsReadOnlySnapshots() {
        gameService.createGame("Quick Draw");

        assertThrows(UnsupportedOperationException.class, () -> gameService.getGames().clear());
        assertTrue(gameService.findGame("QUICK DRAW").isPresent());
    }
}
