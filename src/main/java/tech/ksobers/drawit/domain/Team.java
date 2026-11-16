package tech.ksobers.drawit.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.LongSupplier;

public final class Team extends Entity {

    private final Map<String, Player> playersByName = new LinkedHashMap<>();

    Team(long id, String name) {
        super(id, name);
    }

    synchronized Player addPlayer(String name, LongSupplier idSupplier) {
        String key = nameKey(name);
        if (playersByName.containsKey(key)) {
            throw new DuplicateNameException("player", name);
        }

        Player player = new Player(idSupplier.getAsLong(), name);
        playersByName.put(key, player);
        return player;
    }

    public synchronized Optional<Player> findPlayer(long playerId) {
        return playersByName.values().stream()
                .filter(player -> player.getId() == playerId)
                .findFirst();
    }

    public synchronized Optional<Player> findPlayer(String name) {
        return Optional.ofNullable(playersByName.get(nameKey(name)));
    }

    public synchronized List<Player> getPlayers() {
        return List.copyOf(new ArrayList<>(playersByName.values()));
    }
}
