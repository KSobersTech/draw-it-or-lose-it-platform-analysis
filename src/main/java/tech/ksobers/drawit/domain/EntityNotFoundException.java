package tech.ksobers.drawit.domain;

public final class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityType, long id) {
        super(entityType + " " + id + " was not found.");
    }
}
