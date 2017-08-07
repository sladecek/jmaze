package com.github.sladecek.maze.jmaze.maze;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print3d.ModelFromShapes;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;

import java.util.Random;

/**
 *
 */
public abstract class BaseMaze extends MazeData implements IMaze {
    public BaseMaze() {
        properties = getDefaultProperties().clone();
    }


    @Override
    public MazeProperties getDefaultProperties() {
        MazeProperties defaultProperties = new MazeProperties();
        defaultProperties.put("randomSeed", 0);
        defaultProperties.put("wallHeight", 30.0);
        defaultProperties.put("cellSize", 2.0);
        defaultProperties.put("innerWallSize", 2.0);
        return defaultProperties;
    }

    public void makeMazeAllSteps(boolean with3d) {
        setupRandomGenerator();
        buildMazeGraphAndShapes();
        IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);

        // generate
        pick = g.generateMaze(getGraph());
        pickedShapes = getAllShapes().applyRealization(pick);

        if (with3d) {
            IMaze3DMapper mapper = create3DMapper();
            if (mapper != null) {
                Maze3DSizes sizes;
                IPrintStyle colors;

                double approxRoomSizeInmm = 3;
                sizes = new Maze3DSizes();
                sizes.setCellSizeInmm(2);  // TODO

                colors = new DefaultPrintStyle();


                model3d = ModelFromShapes.make(pickedShapes, mapper, sizes, colors);
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
