package com.github.sladecek.maze.jmaze.properties;

public class MazeValidationError {
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
    private String field;
    private String message;
}
