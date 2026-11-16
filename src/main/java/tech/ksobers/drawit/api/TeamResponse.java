package tech.ksobers.drawit.api;

import java.util.List;

public record TeamResponse(long id, String name, List<PlayerResponse> players) {
}
