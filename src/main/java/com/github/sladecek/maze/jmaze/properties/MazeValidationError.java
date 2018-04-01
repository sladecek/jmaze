package com.github.sladecek.maze.jmaze.properties;
//REV1
/**
 * Exception class for maze validation error. Each error is connected
 * with a maze property via the field attribute.
 */
class MazeValidationError {
    public MazeValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    private final String field;
    private final String message;
}
