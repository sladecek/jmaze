package com.github.sladecek.maze.jmaze.properties;

import java.util.ArrayList;

/**
 * Collection of maze validation errors.
 */
public class MazeValidationErrors {
    public void addError(String error) {
        errors.add(error);
    }

    public Iterable<String> asStrings() {
        return errors;
    }

    ArrayList<String> errors = new ArrayList<>();
}
