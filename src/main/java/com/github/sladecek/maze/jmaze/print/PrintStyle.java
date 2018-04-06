package com.github.sladecek.maze.jmaze.print;
//REV1
import com.github.sladecek.maze.jmaze.print.Color;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;

/**
 * Printout properties.
 */
public final class PrintStyle {

    public void configureFromProperties(MazeProperties properties) {
        printAllWalls = properties.getBoolean("printAllWalls");
        printSolution = properties.getBoolean("printSolution");

        startMarkColor = properties.getColor("startMarkColor");
        targetMarkColor = properties.getColor("targetMarkColor");
        solutionMarkColor = properties.getColor("solutionMarkColor");

        innerWallWidth = properties.getInt("innerWallWidth");
        outerWallWidth = properties.getInt("outerWallWidth");

        startMarkWidth = properties.getInt("startMarkWidth");
        targetMarkWidth = properties.getInt("targetMarkWidth");
        solutionMarkWidth = properties.getInt("solutionMarkWidth");

        outerWallColor = properties.getColor("outerWallColor");
        innerWallColor = properties.getColor("innerWallColor");
        debugWallColor = properties.getColor("debugWallColor");
    }


    public boolean isPrintSolution() {
        return printSolution;
    }

    public boolean isPrintAllWalls() {
        return printAllWalls;
    }

    public Color getOuterWallColor() {
        return outerWallColor;
    }

    public Color getInnerWallColor() {
        return innerWallColor;
    }

    public Color getDebugWallColor() {
        return debugWallColor;
    }

    public int getInnerWallWidth() {
        return innerWallWidth;
    }

    public int getOuterWallWidth() {
        return outerWallWidth;
    }

    public Color getSolutionMarkColor() {
        return solutionMarkColor;
    }

    public Color getStartMarkColor() {
        return startMarkColor;
    }

    public Color getTargetMarkColor() {
        return targetMarkColor;
    }


    public int getSolutionMarkWidth() {
        return solutionMarkWidth;
    }

    public int getStartMarkWidth() {
        return startMarkWidth;
    }


    public int getTargetMarkWidth() {
        return targetMarkWidth;
    }


    private boolean printSolution = true;
    private boolean printAllWalls = false;

    private int startMarkWidth = 4;
    private int targetMarkWidth = 4;
    private int solutionMarkWidth = 2;

    private Color startMarkColor = new Color("ff0000");
    private Color targetMarkColor = new Color("00ff00");
    private Color solutionMarkColor = new Color("777777");

    private Color outerWallColor = new Color("000000");
    private Color innerWallColor = new Color("000000");
    private Color debugWallColor = new Color("eeeeff");
    private int innerWallWidth = 1;
    private int outerWallWidth = 2;

}