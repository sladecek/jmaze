package com.github.sladecek.maze.jmaze.properties;

import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;

import java.util.HashMap;

import static javax.swing.UIManager.put;

/**
 * Combined properties for several maze types. The keys are prefixed by maze type.
 *
 * TODO smazat

public class CombinedOptions extends MazeOptions {
    public CombinedOptions(Iterable<Class> optionClasses) throws IllegalAccessException, InstantiationException {
        for(Class c: optionClasses) {
            MazeOptions o = (MazeOptions) c.newInstance();
            String mazeType = o.getName();
            assert (!mazeType.contains("_")): "Maze type may not contain an underscore.";
            for (MazeOption opt: o.ownOptions) {
                String key = mazeType+"_"+opt.getName();
                ownOptions.add(new MazeOption(key, opt.get))
                put(, entry.getValue());
            }
        }
    }

    public void addPropertiesForType(String mazeType, MazeProperties properties) {
    }

    public MazeProperties extractPropertiesForType(String mazeType) {
        MazeProperties result = new MazeProperties();
        for (HashMap.Entry<String, Object> entry : getData().entrySet()) {
            int i = entry.getKey().indexOf('_');
            if (entry.getKey().substring(0, i) == mazeType) {
                result.put(entry.getKey().substring(i+1), entry.getValue());
            }
        }
        return result;
    }

    @Override
    public boolean canBePrintedIn2D() {
        assert false: "The method canBePrintedIn2D() makes no sense for combined maze.";
        return false;
    }

    @Override
    public boolean canBePrintedIn3D() {
        assert false: "The method canBePrintedIn3D() makes no sense for combined maze.";
        return false;
    }

    @Override
    public String getName() {
        assert false: "The method getName() makes no sense for combined maze.";
        return null;
    }

    @Override
    public Class getMazeClass() {
        assert false: "The method getMazeClass() makes no sense for combined maze.";
        return null;
    }
}
*/