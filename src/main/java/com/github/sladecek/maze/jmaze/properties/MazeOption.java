package com.github.sladecek.maze.jmaze.properties;

import com.github.sladecek.maze.jmaze.print.Color;

import java.util.Locale;

/**
 * A configurable maze property such as width or height.
 */
@SuppressWarnings("UnusedReturnValue")  // unused fluent interface
public class MazeOption {

    public MazeOption(String name, int value, int min, int max) {
        this.name = name;
        this.defaultValue = value;
        this.min = min;
        this.max = max;
        this.step = 1.0;
    }

    public MazeOption(String name, boolean value) {
        this.name = name;
        this.defaultValue = value;
    }

    public MazeOption(String name, String value) {
        this.name = name;
        this.defaultValue = value;
    }

    public MazeOption(String name, Color value) {
        this.name = name;
        this.defaultValue = value;
    }

    public MazeOption(String name, double value, double min, double max, double step) {
        this.name = name;
        this.defaultValue = value;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public String getName() {
        return name;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public Double getStep() {
        return step;
    }

    public MazeOption setValue(Object v) {
        defaultValue = v;
        return this;
    }

    public String getEditor() {
        return editor;
    }

    public MazeOption setEditor(String editor) {
        this.editor = editor;
        return this;
    }


    private final String name;
    private Object defaultValue;
    private double min;
    private double max;
    private double step;

    private OptionLevel level = OptionLevel.Basic;


    public OptionLevel getLevel() {
        return level;
    }

    public MazeOption setLevel(OptionLevel value) {
        this.level = value;
        return this;
    }

    public void convertAndValidate(MazeProperties properties, Locale locale, String prefix, MazeValidationErrors errors) {

        // TODO zkombinovat s mazeProperties .update from properties
        String key = getName(); // TODO ?

        Object value = properties.get(key);
        if (value == null && ! (defaultValue instanceof Boolean)) {
            return;
        }
        if (defaultValue instanceof Integer) {
            if (value instanceof String) {
                try {
                    value = Integer.parseInt((String) value);
                    properties.put(name, value);
                } catch (NumberFormatException e) {
                    errors.addError(prefix, key, "TODO number err");
                }
            }

        }
        else if (defaultValue instanceof Double) {
            if (value instanceof String) {
                try {
                    value = Double.parseDouble((String) value);
                    properties.put(name, value);
                } catch (NumberFormatException e) {
                    errors.addError(prefix, key, "TODO number err");
                }
            }
        }
        else if (defaultValue instanceof Boolean) {
            if (value instanceof String) {
                try {
                    value = Boolean.parseBoolean((String) value);
                    properties.put(name, value);
                } catch (NumberFormatException e) {
                    errors.addError(prefix, key, "TODO number err");
                }
            } else if (value == null) {
                value = false;
                properties.put(name, value);
            }
        }

        if (defaultValue instanceof Number && value instanceof Number ) {
            double dv = ((Number) value).doubleValue();
            if (dv < min || dv > max) {
                errors.addError(prefix, key, "TODO range err");
            }
        }

        // TODO other types
    }

    private String editor = null;
}
