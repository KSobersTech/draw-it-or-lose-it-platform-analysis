package tech.ksobers.drawit.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final Map<String, Game> gamesByName = new LinkedHashMap<>();
    private final AtomicLong nextGameId = new AtomicLong(1);
    private final AtomicLong nextTeamId = new AtomicLong(1);
    private final AtomicLong nextPlayerId = new AtomicLong(1);

    public synchronized Game createGame(String name) {
        String key = Entity.nameKey(name);
        if (gamesByName.containsKey(key)) {
            throw new DuplicateNameException("game", name);
        }

        Game game = new Game(nextGameId.getAndIncrement(), name);
        gamesByName.put(key, game);
        return game;
    }

    public synchronized List<Game> getGames() {
        return List.copyOf(new ArrayList<>(gamesByName.values()));
    }

    public synchronized Optional<Game> findGame(long gameId) {
        return gamesByName.values().stream()
                .filter(game -> game.getId() == gameId)
                .findFirst();
    }

    public synchronized Optional<Game> findGame(String name) {
        return Optional.ofNullable(gamesByName.get(Entity.nameKey(name)));
    }

    public Team addTeam(long gameId, String name) {
        Game game = requireGame(gameId);
        return game.addTeam(name, nextTeamId::getAndIncrement);
    }

    public Player addPlayer(long gameId, long teamId, String name) {
        Team team = requireTeam(gameId, teamId);
        return team.addPlayer(name, nextPlayerId::getAndIncrement);
    }

    public Game requireGame(long gameId) {
        return findGame(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game", gameId));
    }

    public Team requireTeam(long gameId, long teamId) {
        return requireGame(gameId).findTeam(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team", teamId));
    }

    public Player requirePlayer(long gameId, long teamId, long playerId) {
        return requireTeam(gameId, teamId).findPlayer(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player", playerId));
    }
}
