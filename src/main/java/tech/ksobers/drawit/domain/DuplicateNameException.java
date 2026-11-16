package tech.ksobers.drawit.domain;

public final class DuplicateNameException extends RuntimeException {

    public DuplicateNameException(String entityType, String name) {
        super("A " + entityType + " named '" + String.valueOf(name).trim() + "' already exists in this scope.");
    }
}
