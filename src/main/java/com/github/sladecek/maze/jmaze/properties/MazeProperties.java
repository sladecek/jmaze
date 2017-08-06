package com.github.sladecek.maze.jmaze.properties;

import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.printstyle.Color;

import java.util.HashMap;
import java.util.Properties;

/**
 * List of configurable maze properties such as sizes, probabilities or colors.
 */
public class MazeProperties {
    public boolean hasProperty(String name) {
        return data.containsKey(name);

    }

    public MazeProperties clone() {
        MazeProperties cloned = new MazeProperties();
        cloned.data = (HashMap<String, Object>) data.clone();
        return cloned;
    }

    private Object get(String name, java.lang.Class cl) {
        Object o =  data.get(name);
        if (o == null) {
            throw new IllegalArgumentException("Property "+name+" is not present.");
        }
        if (cl.isInstance(o.getClass())) {
            throw new IllegalArgumentException("Property "+name+" is not a "+cl.getName()+" but a." + o.getClass().getName());
        }
        return o;
    }


    public int getInt(String name) {
        return (Integer)get(name, Integer.class);
    }

    public double getDouble(String name) {
        return (Double)get(name, Double.class);
    }

    public Color getColor(String name) {
        return (Color)get(name, Color.class);
    }

    public void put(String name, Object value) {
        data.put(name, value);
    }

    public String getString(String name) {
        return (String)get(name, String.class);
    }

    private HashMap<String, Object> data = new HashMap<>();
}