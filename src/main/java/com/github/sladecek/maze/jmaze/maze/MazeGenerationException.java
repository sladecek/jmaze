package com.github.sladecek.maze.jmaze.maze;

/*
 * Wrapper exception for maze generation errors.
 */
public class MazeGenerationException extends Exception {
    
   
    @SuppressWarnings("SameParameterValue")
    public MazeGenerationException(String message) {
        super(message);
    }

    public MazeGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    private static final long serialVersionUID = 1L;
}
