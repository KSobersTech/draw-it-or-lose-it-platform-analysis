package tech.ksobers.drawit.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import tech.ksobers.drawit.domain.Entity;

public record CreateNamedEntityRequest(
        @NotBlank(message = "name is required")
        @Size(max = Entity.MAX_NAME_LENGTH, message = "name must be at most 80 characters")
        String name) {
}
