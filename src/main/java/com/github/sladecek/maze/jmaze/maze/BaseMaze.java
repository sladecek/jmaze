package com.github.sladecek.maze.jmaze.maze;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.ModelFromShapes;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;

import java.util.Random;

/**
 *  Builds a maze using maze data and IMaze methods.
 */
public abstract class BaseMaze extends MazeData implements IMaze {
    public BaseMaze() {
        properties = getDefaultProperties().clone();
    }


    @Override
    public MazeProperties getDefaultProperties() {
        MazeProperties defaultProperties = new MazeProperties();
        defaultProperties.put("randomSeed", 0);
        return defaultProperties;
    }

    protected void addDefault2DProperties(MazeProperties properties) {
        properties.put("wallHeight", 6.0);

    }

    protected void addDefault3DProperties(MazeProperties properties) {
        properties.put("wallHeight", 30.0);
        properties.put("cellSize", 2.0);
        properties.put("innerWallSize", 2.0);
    }

    public void makeMazeAllSteps(boolean with3d) {
        setupRandomGenerator();
        buildMazeGraphAndShapes();
        IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);

        // generate
        pick = g.generatePick(getGraph());
        pickedShapes = getAllShapes().applyRealization(pick);

        if (with3d) {
            IMaze3DMapper mapper = create3DMapper();
            if (mapper != null) {

                IPrintStyle colors;

                double approxRoomSizeInmm = 3;
                colors = new DefaultPrintStyle();

// TODO proc nasobit?
                model3d = ModelFromShapes.make(pickedShapes, mapper, colors, properties.getDouble("cellSize")*properties.getDouble("innerWallSize"));
            }
        }

    }


    protected void setupRandomGenerator() {
        int randomSeed = properties.getInt("randomSeed");
        randomGenerator = new Random();
        randomGenerator.setSeed(randomSeed);
    }

    // for unit tests
    public void forceRandomGenerator(Random r) {
        randomGenerator = r;
    }


}
