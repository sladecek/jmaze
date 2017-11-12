package com.github.sladecek.maze.jmaze.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Collection of maze validation errors.
 */
public class MazeValidationErrors {
    public void addError(MazeValidationError error) {
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

    ArrayList<MazeValidationError> errors = new ArrayList<>();

    public boolean hasErrorForField(String field) {
        if (isEmpty()) return false;
        return errors.stream().filter((e)->e.getField().equals(field)).count() > 0;
    }

    public ArrayList<String> errorsForField(String field) {
        return errors.stream()
                .filter((e)->e.getField().equals(field))
                .map((e)->e.getMessage())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
