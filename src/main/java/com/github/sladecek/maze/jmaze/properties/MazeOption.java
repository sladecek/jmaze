package com.github.sladecek.maze.jmaze.properties;

import com.github.sladecek.maze.jmaze.printstyle.Color;

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

    private String name;
    private Object defaultValue;
    private double min;
    private double max;
    private double step;

}
