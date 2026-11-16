package tech.ksobers.drawit.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameApiIntegrationTest {

    private static final String JSON = "application/json";

    @LocalServerPort
    private int port;

    private final HttpClient client = HttpClient.newHttpClient();

    @Test
    void exercisesTheImplementedApiSlice() throws Exception {
        HttpResponse<String> emptyGames = send("GET", "/api/v1/games", null);
        assertEquals(200, emptyGames.statusCode());
        assertEquals("[]", emptyGames.body());

        HttpResponse<String> createdGame = send("POST", "/api/v1/games", "{\"name\":\"Quick Draw\"}");
        assertEquals(201, createdGame.statusCode());
        assertTrue(createdGame.headers().firstValue("Location").orElseThrow().endsWith("/api/v1/games/1"));
        assertTrue(createdGame.body().contains("\"name\":\"Quick Draw\""));

        HttpResponse<String> duplicate = send("POST", "/api/v1/games", "{\"name\":\" quick draw \"}");
        assertEquals(409, duplicate.statusCode());
        assertTrue(duplicate.body().contains("DUPLICATE_NAME"));

        HttpResponse<String> invalid = send("POST", "/api/v1/games", "{\"name\":\"   \"}");
        assertEquals(400, invalid.statusCode());
        assertTrue(invalid.body().contains("VALIDATION_FAILED"));

        HttpResponse<String> createdTeam = send(
                "POST",
                "/api/v1/games/1/teams",
                "{\"name\":\"Moonlight\"}");
        assertEquals(201, createdTeam.statusCode());
        assertTrue(createdTeam.headers().firstValue("Location").orElseThrow()
                .endsWith("/api/v1/games/1/teams/1"));
        assertTrue(createdTeam.body().contains("\"name\":\"Moonlight\""));

        HttpResponse<String> team = send("GET", "/api/v1/games/1/teams/1", null);
        assertEquals(200, team.statusCode());
        assertTrue(team.body().contains("\"name\":\"Moonlight\""));

        HttpResponse<String> createdPlayer = send(
                "POST",
                "/api/v1/games/1/teams/1/players",
                "{\"name\":\"Keishmar\"}");
        assertEquals(201, createdPlayer.statusCode());
        assertTrue(createdPlayer.headers().firstValue("Location").orElseThrow()
                .endsWith("/api/v1/games/1/teams/1/players/1"));
        assertTrue(createdPlayer.body().contains("\"name\":\"Keishmar\""));

        HttpResponse<String> player = send("GET", "/api/v1/games/1/teams/1/players/1", null);
        assertEquals(200, player.statusCode());
        assertTrue(player.body().contains("\"name\":\"Keishmar\""));

        HttpResponse<String> game = send("GET", "/api/v1/games/1", null);
        assertEquals(200, game.statusCode());
        assertTrue(game.body().contains("\"teams\":[{"));
        assertTrue(game.body().contains("\"players\":[{"));

        HttpResponse<String> missing = send("GET", "/api/v1/games/999", null);
        assertEquals(404, missing.statusCode());
        assertTrue(missing.body().contains("RESOURCE_NOT_FOUND"));

        HttpResponse<String> health = send("GET", "/actuator/health", null);
        assertEquals(200, health.statusCode());
        assertTrue(health.body().contains("\"status\":\"UP\""));
    }

    private HttpResponse<String> send(String method, String path, String body)
            throws IOException, InterruptedException {
        HttpRequest.Builder request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path));
        if (body == null) {
            request.method(method, HttpRequest.BodyPublishers.noBody());
        } else {
            request.header("Content-Type", JSON)
                    .method(method, HttpRequest.BodyPublishers.ofString(body));
        }
        return client.send(request.build(), HttpResponse.BodyHandlers.ofString());
    }
}
