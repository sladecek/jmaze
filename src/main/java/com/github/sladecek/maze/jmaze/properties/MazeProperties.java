package com.github.sladecek.maze.jmaze.properties;

import com.github.sladecek.maze.jmaze.maze.MazeGenerationException;
import com.github.sladecek.maze.jmaze.print.Color;

import java.io.*;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * List of configurable maze properties such as sizes, probabilities or colors.
 */
public class MazeProperties implements java.io.Serializable {
    public boolean hasProperty(String name) {
        return data.containsKey(name);
    }

    public MazeProperties deepCopy() throws MazeGenerationException {

        MazeProperties result = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            out.close();

            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            result = (MazeProperties)in.readObject();
        }
        catch(Throwable e) {
            throw new MazeGenerationException("Internal error by deepCopy()", e);
        }
        return result;
    }

    private Object get(String name, java.lang.Class cl) {
        Object o =  data.get(name);
        if (o == null) {
            throw new IllegalArgumentException("Property '"+name+"' is not present.");
        }
        if (cl.isInstance(o.getClass())) {
            throw new IllegalArgumentException("Property '"+name+"' is not a '"+cl.getName()+"' but a '" + o.getClass().getName());
        }
        return o;
    }

    public int getInt(String name) {
        return (Integer)get(name, Integer.class);
    }

    public double getDouble(String name) {
        return ((Number)get(name, Number.class)).doubleValue();
    }

    public Color getColor(String name) {
        return (Color)get(name, Color.class);
    }

    public Object get(String name) {
        return data.get(name);
    }

    public void put(String name, Object value) {
        data.put(name, value);
    }

    public String getString(String name) {
        return (String)get(name, String.class);
    }

    public boolean getBoolean(String name) {
        return (Boolean)get(name, Boolean.class);
    }

    public boolean getBooleanOrFalse(String name) {
        return data.containsKey(name) &&
            (Boolean)get(name, Boolean.class);
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public void updateFromStrings(MazeProperties stringProperties) {
        stringProperties.data.forEach((k,v)-> {
            if (!hasProperty(k)) {
                throw new IllegalArgumentException("Undefined property '"+k+"'");
            }
            Object o = data.get(k);
            String val = (String)v;
            if (o instanceof Integer) {
                put(k, Integer.valueOf(val));
            } else if (o instanceof Double) {
                put(k, Double.valueOf(val));
            } else if (o instanceof Boolean) {
                put(k, Boolean.valueOf(val));
            } else if (o instanceof Color) {
                put(k, new Color(val));
            } else {
                assert (o instanceof  String);
                put(k, val);
            }
        });
    }

    public String toUserString() {
        return data.keySet().stream().map((k)-> k + "=" + data.get(k).toString()).collect(Collectors.joining("\n"));
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void update(MazeProperties partialProperties) {
        partialProperties.data.forEach(data::put);
    }

    public void mergeDefaults(MazeProperties properties) {
        data.forEach((k,v)-> {if (!properties.hasProperty(k)) properties.put(k,v);});
    }

    private final HashMap<String, Object> data = new HashMap<>();
}
