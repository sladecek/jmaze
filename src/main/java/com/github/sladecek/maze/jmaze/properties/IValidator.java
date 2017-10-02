package com.github.sladecek.maze.jmaze.properties;

import java.util.Locale;

/**
 * Interface for maze parameter validators.
 */
public interface IValidator {
    MazeValidationErrors convertAndValidate(MazeProperties properties, Locale locale);

}
