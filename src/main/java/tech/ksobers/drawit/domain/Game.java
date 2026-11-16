package tech.ksobers.drawit.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.LongSupplier;

public final class Game extends Entity {

    private final Map<String, Team> teamsByName = new LinkedHashMap<>();

    Game(long id, String name) {
        super(id, name);
    }

    synchronized Team addTeam(String name, LongSupplier idSupplier) {
        String key = nameKey(name);
        if (teamsByName.containsKey(key)) {
            throw new DuplicateNameException("team", name);
        }

        Team team = new Team(idSupplier.getAsLong(), name);
        teamsByName.put(key, team);
        return team;
    }

    public synchronized Optional<Team> findTeam(long teamId) {
        return teamsByName.values().stream()
                .filter(team -> team.getId() == teamId)
                .findFirst();
    }

    public synchronized Optional<Team> findTeam(String name) {
        return Optional.ofNullable(teamsByName.get(nameKey(name)));
    }

    public synchronized List<Team> getTeams() {
        return List.copyOf(new ArrayList<>(teamsByName.values()));
    }
}
