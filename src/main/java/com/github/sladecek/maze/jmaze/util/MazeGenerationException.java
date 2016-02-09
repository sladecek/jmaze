package com.github.sladecek.maze.jmaze.util;

/*
 * Wrapper exception for maze generation errors.
 */
public class MazeGenerationException extends Exception {
    
   
    public MazeGenerationException(String message) {
        super(message);
    }

    public MazeGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    private static final long serialVersionUID = 1L;
}
