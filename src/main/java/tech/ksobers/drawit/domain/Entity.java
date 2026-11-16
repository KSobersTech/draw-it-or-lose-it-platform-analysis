package tech.ksobers.drawit.domain;

import java.util.Locale;
import java.util.Objects;

public abstract class Entity {

    public static final int MAX_NAME_LENGTH = 80;

    private final long id;
    private final String name;

    protected Entity(long id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be greater than zero");
        }

        this.id = id;
        this.name = validateName(name);
    }

    private static String validateName(String name) {
        String cleanedName = Objects.requireNonNull(name, "name must not be null").trim();
        if (cleanedName.isEmpty()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (cleanedName.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("name must be at most " + MAX_NAME_LENGTH + " characters");
        }
        return cleanedName;
    }

    public final long getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    static String nameKey(String name) {
        return validateName(name).toLowerCase(Locale.ROOT);
    }
}
