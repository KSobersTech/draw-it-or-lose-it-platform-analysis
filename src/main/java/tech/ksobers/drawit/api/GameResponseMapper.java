package tech.ksobers.drawit.api;

import tech.ksobers.drawit.domain.Game;
import tech.ksobers.drawit.domain.Player;
import tech.ksobers.drawit.domain.Team;

final class GameResponseMapper {

    private GameResponseMapper() {
    }

    static GameResponse toResponse(Game game) {
        return new GameResponse(
                game.getId(),
                game.getName(),
                game.getTeams().stream().map(GameResponseMapper::toResponse).toList());
    }

    static TeamResponse toResponse(Team team) {
        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getPlayers().stream().map(GameResponseMapper::toResponse).toList());
    }

    static PlayerResponse toResponse(Player player) {
        return new PlayerResponse(player.getId(), player.getName());
    }
}
