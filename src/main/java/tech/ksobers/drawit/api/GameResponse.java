package tech.ksobers.drawit.api;

import java.util.List;

public record GameResponse(long id, String name, List<TeamResponse> teams) {
}
