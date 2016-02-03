package com.github.sladecek.maze.jmaze.shapes;

public class ShapeContext {

    public ShapeContext(boolean isPolarCoordinates, int pictureHeight,
            int pictureWidth, int resolution) {
        super();
        this.isPolarCoordinates = isPolarCoordinates;
        this.pictureHeight = pictureHeight;
        this.pictureWidth = pictureWidth;
        this.resolution = resolution;
    }
    
    private boolean isPolarCoordinates;
    private int pictureHeight;
    private int pictureWidth;
    private int resolution;
    
    public boolean isPolarCoordinates() {
        return isPolarCoordinates;
    }
    public int getPictureHeight() {
        return pictureHeight;
    }
    public int getPictureWidth() {
        return pictureWidth;
    }
    public int getResolution() {
        return resolution;
    }
}
