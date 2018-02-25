package com.github.sladecek.maze.jmaze.properties;

import com.github.sladecek.maze.jmaze.print.Color;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A configurable maze property such as width or height.
 */
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

    public OptionLevel getLevel() {
        return level;
    }

    public MazeOption setLevel(OptionLevel value) {
        this.level = value;
        return this;
    }

    public void convertAndValidate(MazeProperties properties, Locale locale, String prefix, MazeValidationErrors errors) {
        String key = getName();
        Object value = properties.get(key);


        if (value == null && !(defaultValue instanceof Boolean)) {
            return;
        }

        if ((value instanceof String) && !(defaultValue instanceof Boolean) && !(defaultValue instanceof Number))
        {
            throw new UnsupportedOperationException("So far, only booleans and numbers can be converted from string.");
        }

        ResourceBundle messages = ResourceBundle.getBundle("jMazeMessages", locale);

        if (defaultValue instanceof Integer) {
            if (value instanceof String) {
                try {
                    value = Integer.parseInt((String) value);
                    properties.put(name, value);
                } catch (NumberFormatException e) {
                    errors.addError(prefix, key, messages.getString("integer_format_error"));
                }
            }

        } else if (defaultValue instanceof Double) {
            if (value instanceof String) {
                try {
                    value = Double.parseDouble((String) value);
                    properties.put(name, value);
                } catch (NumberFormatException e) {
                    errors.addError(prefix, key, messages.getString("double_format_error"));
                }
            }
        } else if (defaultValue instanceof Boolean) {
            if (value instanceof String) {
                if (((String) value).equals("true")) {
                    properties.put(name, true);
                } else if (((String) value).equals("false")) {
                    properties.put(name, false);
                } else {
                    final String e = messages.getString("boolean_format_error");
                    MessageFormat formatter = new MessageFormat(e);
                    formatter.setLocale(locale);

                    errors.addError(prefix, key, formatter.format(new Object[]{value}));
                }
            } else if (value == null) {
                properties.put(name, false);
            }
        }

        if (defaultValue instanceof Number && value instanceof Number) {
            double dv = ((Number) value).doubleValue();
            if (dv < min || dv > max) {
                final String e = messages.getString("number_range_error");
                MessageFormat formatter = new MessageFormat(e);
                formatter.setLocale(locale);

                errors.addError(prefix, key, formatter.format(new Object[]{min, max, dv}));
            }
        }

    }

    private final String name;
    private Object defaultValue;
    private double min;
    private double max;
    private double step;
    private OptionLevel level = OptionLevel.Basic;
    private String editor = null;

}
