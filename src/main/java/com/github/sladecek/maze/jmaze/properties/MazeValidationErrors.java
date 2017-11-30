package com.github.sladecek.maze.jmaze.properties;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Collection of maze validation errors.
 */
public class MazeValidationErrors {

    private void addError(MazeValidationError error) {
        errors.add(error);
    }

    public Iterable<MazeValidationError> getAll() {
        return errors;
    }

    public void addError(String prefix, String key, String message) {
        String k = key;
        if (!prefix.isEmpty()) {
            k = prefix + "_" + key;
        }
        addError(new MazeValidationError(k, message));
    }

    public boolean isEmpty() {
        return errors.isEmpty();
    }

    private final ArrayList<MazeValidationError> errors = new ArrayList<>();

    public boolean hasErrorForField(String field) {
        if (isEmpty()) {
            return false;
        } else {
            return errors
                    .stream()
                    .filter((e) -> e.getField().equals(field))
                    .count() > 0;
        }
    }

    public ArrayList<String> errorsForField(String field) {
        return errors.stream()
                .filter((e)->e.getField().equals(field))
                .map(MazeValidationError::getMessage)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
