package tech.ksobers.drawit.api;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import tech.ksobers.drawit.domain.Game;
import tech.ksobers.drawit.domain.GameService;
import tech.ksobers.drawit.domain.Player;
import tech.ksobers.drawit.domain.Team;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<GameResponse> getGames() {
        return gameService.getGames().stream()
                .map(GameResponseMapper::toResponse)
                .toList();
    }

    @GetMapping("/{gameId}")
    public GameResponse getGame(@PathVariable long gameId) {
        return GameResponseMapper.toResponse(gameService.requireGame(gameId));
    }

    @PostMapping
    public ResponseEntity<GameResponse> createGame(@Valid @RequestBody CreateNamedEntityRequest request) {
        Game game = gameService.createGame(request.name());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{gameId}")
                .buildAndExpand(game.getId())
                .toUri();
        return ResponseEntity.created(location).body(GameResponseMapper.toResponse(game));
    }

    @PostMapping("/{gameId}/teams")
    public ResponseEntity<TeamResponse> addTeam(
            @PathVariable long gameId,
            @Valid @RequestBody CreateNamedEntityRequest request) {
        Team team = gameService.addTeam(gameId, request.name());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{teamId}")
                .buildAndExpand(team.getId())
                .toUri();
        return ResponseEntity.created(location).body(GameResponseMapper.toResponse(team));
    }

    @GetMapping("/{gameId}/teams/{teamId}")
    public TeamResponse getTeam(@PathVariable long gameId, @PathVariable long teamId) {
        return GameResponseMapper.toResponse(gameService.requireTeam(gameId, teamId));
    }

    @PostMapping("/{gameId}/teams/{teamId}/players")
    public ResponseEntity<PlayerResponse> addPlayer(
            @PathVariable long gameId,
            @PathVariable long teamId,
            @Valid @RequestBody CreateNamedEntityRequest request) {
        Player player = gameService.addPlayer(gameId, teamId, request.name());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{playerId}")
                .buildAndExpand(player.getId())
                .toUri();
        return ResponseEntity.created(location).body(GameResponseMapper.toResponse(player));
    }

    @GetMapping("/{gameId}/teams/{teamId}/players/{playerId}")
    public PlayerResponse getPlayer(
            @PathVariable long gameId,
            @PathVariable long teamId,
            @PathVariable long playerId) {
        return GameResponseMapper.toResponse(gameService.requirePlayer(gameId, teamId, playerId));
    }
}
