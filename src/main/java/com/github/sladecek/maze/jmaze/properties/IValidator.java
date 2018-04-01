package com.github.sladecek.maze.jmaze.properties;
//REV1
import java.util.Locale;

/**
 * Interface for maze parameter validators.
 */
interface IValidator {
    MazeValidationErrors convertAndValidate(MazeProperties properties, String prefix, Locale locale);
}
