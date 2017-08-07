package com.github.sladecek.maze.jmaze.maze;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;
import com.github.sladecek.maze.jmaze.print3d.Maze3DSizes;
import com.github.sladecek.maze.jmaze.print3d.ModelFromShapes;
import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

import java.util.Random;

/**
 *
 */
public abstract class BaseMaze implements IMaze {
    public BaseMaze() {
        defaultProperties.put("randomSeed", 0);
        defaultProperties.put("wallHeight", 10.0);
        defaultProperties.put("cellSize", 2.0);
        defaultProperties.put("innerWallToPixelRatio", 1.0);
    }

    /**
     * Set properties for concrete maze such as size or colors.
     * @param value
     */

    public void setProperties(MazeProperties value) {
        properties = value;
    }

    @Override
    public MazeProperties getDefaultProperties() {
        return defaultProperties;
    }



    public String getName() {
        return defaultProperties.getString("name");
    }


    public GenericMazeStructure getGraph() {
        return graph;
    }

    public Model3d getModel3d() {
        return model3d;
    }

    public ShapeContainer getRealisedModel() {

        return realisedModel;
    }


    public ShapeContainer getFlatModel() {
        return flatModel;

    }

    public void makeMazeAllSteps(boolean with3d) {
        setupRandomGenerator();
        buildMazeGraphAndShapes();
        IMazeGenerator g = new DepthFirstMazeGenerator(randomGenerator);

        // generate
        realization = g.generateMaze(getGraph());
        realisedModel = getFlatModel().applyRealization(realization);

        if (with3d) {
            IMaze3DMapper mapper = create3DMapper();
            if (mapper != null) {
                Maze3DSizes sizes;
                IPrintStyle colors;

                double approxRoomSizeInmm = 3;
                sizes = new Maze3DSizes();
                sizes.setCellSizeInmm(2);  // TODO

                colors = new DefaultPrintStyle();


                model3d = ModelFromShapes.make(realisedModel, mapper, sizes, colors);
            }
        }

    }

    protected void  setupRandomGenerator() {
        int randomSeed = properties.getInt("randomSeed");
        randomGenerator = new Random();
        randomGenerator.setSeed(randomSeed);
    }

    // for unit tests
    public void forceRandomGenerator(Random r) {
        randomGenerator = r;
    }

    protected Random randomGenerator;

    protected GenericMazeStructure graph = new GenericMazeStructure();
    protected ShapeContainer flatModel;
    protected ShapeContainer realisedModel;
    protected MazeRealization realization;
    protected Model3d model3d;


    protected MazeProperties defaultProperties = new MazeProperties();
    protected MazeProperties properties;

}
