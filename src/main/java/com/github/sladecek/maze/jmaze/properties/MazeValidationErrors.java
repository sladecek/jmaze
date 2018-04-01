package com.github.sladecek.maze.jmaze.properties;
//REV1
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Collection of maze validation errors.
 */
public class MazeValidationErrors {


    /**
     * Returns all errors in the collection.
     *
     * @return all errors.
     */
    public Iterable<MazeValidationError> getAll() {
        return errors;
    }

    /**
     * Add one error to the collection.
     *
     * @param prefix  Error key prefix, usually maze type.
     * @param key     Field key
     * @param message
     */
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

    public boolean hasErrorForField(String field) {
        if (isEmpty()) {
            return false;
        }

        return errors
                .stream()
                .filter((e) -> e.getField().equals(field))
                .count() > 0;

    }

    public ArrayList<String> errorsForField(String field) {
        return errors.stream()
                .filter((e) -> e.getField().equals(field))
                .map(MazeValidationError::getMessage)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void addError(MazeValidationError error) {
        errors.add(error);
    }

    private final ArrayList<MazeValidationError> errors = new ArrayList<>();
}
